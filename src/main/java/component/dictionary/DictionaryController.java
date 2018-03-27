package component.dictionary;

import common.ExcelLoader;
import common.FileOperations;
import component.Controller;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;


public class DictionaryController implements Controller{

    // Sheet indexes
    private static final int MANDATORY_SHEET = 0;
    private static final int AMBIGUITY_SHEET = 1;
    private static final int DICTIONARY_SHEET = 2;
    private static final int VOWELS_SHEET = 3;
    private static final int MODIFIERS_SHEET = 4;


    @FXML
    private TextArea contentTextArea;

    @FXML
    private Button browseButton;

    @FXML
    private Button loadTextButton;

    @FXML
    private Button fixMandatoryButton;

    @FXML
    private Button fixAmbiguityButton;

    @FXML
    private Button checkLegitimacyButton;

    private Tooltip tooltip;


    String fileName;
    String outputText;

    String RuleArray[][], ambiguousChars[][];
    String vowels[], modifiers[];
    Set<String> dictionaryWordList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tooltip = new Tooltip();
        tooltip.autoHideProperty().setValue(true);
        contentTextArea.setTooltip(tooltip);

        fileName = "rules.xls";

        // Show unicode view popup
        contentTextArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MouseButton button = mouseEvent.getButton();
                if (button == MouseButton.MIDDLE || (button == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2)){
                    String selectedText = contentTextArea.getSelectedText();
                    String unicodeString = "";
                    for (char c: selectedText.toCharArray()){
                        unicodeString += "\\u" + Integer.toHexString(c | 0x10000).substring(1) + ": " + "\n";
                    }

                    tooltip.setText(unicodeString);
                    tooltip.show(contentTextArea, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            }
        });


        // On click Load Text button
        loadTextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadOutputText();
            }
        });


        // Apply Mandatory Rules
        fixMandatoryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Load Rules
                ExcelLoader excelLoader = new ExcelLoader(fileName);
                excelLoader.setSheetIndex(MANDATORY_SHEET);
                String[][] rules = excelLoader.loadData();

                // Load text
                loadOutputText();

                // Find and fix
                for (String[] rule: rules){
                    if (rule[0] != null && rule[1] != null){
                        if (outputText.contains(rule[0])){
                            outputText = outputText.replaceAll(rule[0], rule[1]);
                            System.out.println("Fixed Mandatory: " + rule[0] + " => " + rule[1]);
                        }
                    }
                }

                saveOutputText();
            }
        });


        // Check and fix Ambiguities
        fixAmbiguityButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadDictionaryWords();
                loadOutputText();
                String[] outputWords = outputText.split(" ");

                loadAmbiguityChars();

                // Check for the words in the dictionary
                for (String word: outputWords){
                    // If not the word in dictionary
                    if (!dictionaryWordList.contains(word)){
                        // Check for ambiguous options
                        for (String[] s: ambiguousChars){
                            if (s[0] != null && word.contains(s[0])){
                                String newWord = word.replaceAll(s[0], s[1]);
                                // Check for newWord in dictionary
                                if (dictionaryWordList.contains(newWord)){
                                    // Replace by new word
                                    outputText = outputText.replaceAll(word, newWord);
                                    System.out.println("Fixed Ambiguity: " + word + " => " + newWord);
                                }
                            }
                        }
                    }
                }

                saveOutputText();
            }
        });

        // Check legitimacy
        checkLegitimacyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadVowelsAndModifiers();

                loadOutputText();
                String[] words = outputText.split(" ");
                for (String word : words){
                    if (word.length() > 0) {
                        // Check whether the word starting with a vowel modifier
                        for (String m : modifiers){
                            if (word.substring(0, 1).equals(m)){
                                System.out.println("Legitimacy error: Modifier (" + m + " in " + word + ")");
                            }
                        }

                        // Check whether the word contains a vowel in the middle
                        for (int i = 1; i<word.length(); i++){
                            for (String m : vowels){
                                if (m != null && word.charAt(i) == m.charAt(0)){
                                    System.out.println("Legitimacy error: Vowel (" + m + " in " + word + ")");
                                }
                            }
                        }

                    }
                }


                saveOutputText();
            }
        });


    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onRefresh() {

    }

    // Load recognnized text
    public void loadOutputText(){
        // Load recognized text file
        FileOperations fo = new FileOperations();
        outputText = fo.openFile("sample.txt");

        contentTextArea.setText(outputText);
        System.out.println("Loaded output text");
    }

    public void saveOutputText(){
        // TODO: Save

        contentTextArea.setText(outputText);
    }


    // Load Dictionary words
    public void loadDictionaryWords(){
        // Load data
        ExcelLoader excelLoader = new ExcelLoader(fileName);
        excelLoader.setSheetIndex(DICTIONARY_SHEET);
        String[][] data = excelLoader.loadData();

        int wordCount = 0;
        String[] tempList = new String[data.length * data[0].length];
        for (String[] row: data){
            for (String val: row){
                if (val != null){
                    tempList[wordCount] = val;
                    wordCount++;
                }
            }
        }

        // Create hashset of words
        dictionaryWordList = new HashSet<String>(Arrays.asList(Arrays.copyOfRange(tempList, 0, wordCount)));

    }

    // Load Ambiguours rules
    public void loadAmbiguityChars(){
        // Load data
        ExcelLoader excelLoader = new ExcelLoader(fileName);
        excelLoader.setSheetIndex(AMBIGUITY_SHEET);
        ambiguousChars = excelLoader.loadData();
    }


    // Load vowels and modifiers
    public void loadVowelsAndModifiers(){
        // Load data
        String[][] data;
        ExcelLoader excelLoader = new ExcelLoader(fileName);

        // Load vowels
        excelLoader.setSheetIndex(VOWELS_SHEET);
        data = excelLoader.loadData();
        vowels = new String[data.length];
        for (int i=0; i<data.length; i++){
            if (data[i][0] != null) vowels[i] = data[i][0];
        }

        // Load modifiers
        excelLoader.setSheetIndex(MODIFIERS_SHEET);
        data = excelLoader.loadData();
        modifiers = new String[data.length];
        for (int i=0; i<data.length; i++){
            if (data[i][0] != null) modifiers[i] = data[i][0];
        }
    }

    // TMP
    public void showArray(String[][] data){
        for (String[] r: data){
            for (String c: r){
                System.out.print(c);
            }
            System.out.println();
        }
    }



}
