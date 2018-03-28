package component.dictionary;

import common.FileOperations;
import common.LanguageData;
import common.util.LangUtils;
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

    private final String languageRulesPath = "tessdata/lang_data.xls";

    private final String tessdataDir = "./tessdata/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set previous filenames
        outputPathTextField.setText(ConfigurationHandler.getOutputTextPath());

        LanguageData.loadLanguageData(languageRulesPath);

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
                LangUtils.fixMandatory(outputPathTextField.getText(), tessdataDir);
                logLabel.setText(LangUtils.getLogBrief());
            }
        });


        // Check and fix Ambiguities
        fixAmbiguityButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LangUtils.fixAmbiguity(outputPathTextField.getText(), tessdataDir);
                logLabel.setText(LangUtils.getLogBrief());
            }
        });

        // Check legitimacy
        checkLegitimacyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LangUtils.checkLegitimacy(outputPathTextField.getText(), tessdataDir);
                logLabel.setText(LangUtils.getLogBrief());
            }
        });


    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onRefresh() {

    }



}
