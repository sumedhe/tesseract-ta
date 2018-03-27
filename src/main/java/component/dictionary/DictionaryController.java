package component.dictionary;

import common.ExcelLoader;
import common.FileOperations;
import component.Controller;
import configuration.ConfigurationHandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
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
    private Button browseButton;

    @FXML
    private TextField rulesPathTextField;

    @FXML
    private TextArea contentTextArea;

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

        // Set previous Rules filename
        rulesPathTextField.setText(ConfigurationHandler.getRulesPath());

        // Browse rules filename
        browseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Select Rules sheet");
                File selectedDirectory = chooser.showOpenDialog(browseButton.getScene().getWindow());
                rulesPathTextField.setText(selectedDirectory.getAbsolutePath());
                ConfigurationHandler.setRulesPath(selectedDirectory.getAbsolutePath());
            }
        });

        
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
                outputText = PostProcessor.fixMandatory(outputText, rules);
                saveOutputText();
            }
        });


        // Check and fix Ambiguities
        fixAmbiguityButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadDictionaryWords();
                loadOutputText();
                loadAmbiguityChars();
                outputText = PostProcessor.fixAmbiguity(outputText, ambiguousChars,  dictionaryWordList);
                saveOutputText();
            }
        });

        // Check legitimacy
        checkLegitimacyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadVowelsAndModifiers();

                loadOutputText();
                String report = PostProcessor.checkLegitimacy(outputText, vowels, modifiers);
                System.out.println(report);
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
