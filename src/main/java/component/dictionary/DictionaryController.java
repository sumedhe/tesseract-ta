package component.dictionary;

import common.FileOperations;
import common.LanguageRules;
import common.PostProcessor;
import component.Controller;
import configuration.ConfigurationHandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.*;


public class DictionaryController implements Controller{

    @FXML
    private TextField rulesPathTextField;
    @FXML
    private Button browseRulesButton;

    @FXML
    private TextField outputPathTextField;
    @FXML
    private Button browseOutputTextButton;

    @FXML
    private Label logLabel;

    @FXML
    private Button fixMandatoryButton;
    @FXML
    private Button fixAmbiguityButton;
    @FXML
    private Button checkLegitimacyButton;
    @FXML
    private Button verifyCharactersButton;

    private final String logPath = "tessdata/log_report.txt";

    private final String languageRulesPath = "tessdata/lang_rules.xls";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set previous filenames
        outputPathTextField.setText(ConfigurationHandler.getOutputTextPath());

        LanguageRules.loadLanguageData(languageRulesPath);

        // Browse rules rulesFileName
        browseOutputTextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Select output file");
                File selectedDirectory = chooser.showOpenDialog(browseOutputTextButton.getScene().getWindow());
                outputPathTextField.setText(selectedDirectory.getAbsolutePath());
                ConfigurationHandler.setOutputTextPath(selectedDirectory.getAbsolutePath());
            }
        });


        // Apply Mandatory Rules
        fixMandatoryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Load text
                String text = loadOutputText();
                text = PostProcessor.fixMandatory(text, LanguageRules.getMandatoryRules());
                logLabel.setText(PostProcessor.getLogBrief());
                saveOutputText(text);
                saveLog(PostProcessor.getLog());
            }
        });


        // Check and fix Ambiguities
        fixAmbiguityButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String text = loadOutputText();
                text = PostProcessor.fixAmbiguity(text, LanguageRules.getAmbiguousChars(),  LanguageRules.getDictionaryWordList());
                logLabel.setText(PostProcessor.getLogBrief());
                saveOutputText(text);
                saveLog(PostProcessor.getLog());
            }
        });

        // Check legitimacy
        checkLegitimacyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String text = loadOutputText();
                String log = PostProcessor.checkLegitimacy(text, LanguageRules.getVowels(), LanguageRules.getModifiers());
                System.out.println(log);
                logLabel.setText(PostProcessor.getLogBrief());
                saveLog(PostProcessor.getLog());
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
    public String loadOutputText(){
        // Load recognized text file
        FileOperations fo = new FileOperations();
        return fo.openFile(ConfigurationHandler.getOutputTextPath());
    }

    public void saveOutputText(String text){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(ConfigurationHandler.getOutputTextPath(), "UTF-8");
            writer.println(text);
            writer.close();
            System.out.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveLog(String text){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(logPath, "UTF-8");
            writer.println(text);
            writer.close();
            System.out.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
