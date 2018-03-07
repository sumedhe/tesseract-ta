package component.dictionary;

import common.ExcelLoader;
import common.FileOperations;
import component.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class DictionaryController implements Controller{
    @FXML
    private TextArea contentTextArea;

    private Tooltip tooltip;

    @FXML
    private Button browseButton;

    @FXML
    private Button loadTextButton;

    @FXML
    private Button applyRulesButton;

    @FXML
    private Button checkAmbiguitiesButton;

    @FXML
    private Button grammarCheckButton;


    String outputText;

    String RuleArray[][], ambiguousChars[][];

    Set<String> dictionaryWordList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tooltip = new Tooltip();
        tooltip.autoHideProperty().setValue(true);
        contentTextArea.setTooltip(tooltip);

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


        // On click Apply Rules button
        applyRulesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Load Rules
                ExcelLoader excelLoader = new ExcelLoader("rules.xls");
                String[][] rules = excelLoader.loadData();

                // Load text
                loadOutputText();

                // Find and fix
                for (String[] rule: rules){
                    if (rule[0] != null && rule[1] != null){
                        outputText = outputText.replaceAll(rule[0], rule[1]);
                    }
                }

                saveOutputText();
            }
        });


        // On click Check Ambiguities button
        checkAmbiguitiesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadDictionaryWords();
                loadOutputText();
                String[] outputWords = outputText.split(" ");

                loadAmbiguoursChars();

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
                                    System.out.println("Fixing Ambiguity: " + word + " by " + newWord);
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
    }

    public void saveOutputText(){
        // TODO: Save

        contentTextArea.setText(outputText);
    }


    // Load Dictionary words
    public void loadDictionaryWords(){
        // Load data
        ExcelLoader excelLoader = new ExcelLoader("rules.xls");
        excelLoader.setSheetIndex(2); // Sheet 2: Dictionary words
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
    public void loadAmbiguoursChars(){
        // Load data
        ExcelLoader excelLoader = new ExcelLoader("rules.xls");
        excelLoader.setSheetIndex(1); // Sheet 1: Ambiguous characters
        ambiguousChars = excelLoader.loadData();
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
