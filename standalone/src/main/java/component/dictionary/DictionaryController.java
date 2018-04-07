package component.dictionary;

import a.LangUtils;
import a.LanguageData;
import component.Controller;
import configuration.ConfigurationHandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryController implements Controller{


    @FXML
    private TextField outputDirectoryTextField;
    @FXML
    private Button browseOutputDirectoryButton;

    @FXML
    private Label logLabel;

    @FXML
    private Button fixMandatoryButton;
    @FXML
    private Button fixAmbiguityButton;
    @FXML
    private Button checkLegitimacyButton;
    @FXML
    private Button checkExBlocksButton;

    @FXML
    private Button clearLogButton;
    @FXML
    private Button reloadLanguageDataButton;

    private final String tessconfigDir = "./tessconfig/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set previous filenames
        outputDirectoryTextField.setText(ConfigurationHandler.getOutputDirectoryPath());

        LanguageData.loadLanguageData(tessconfigDir + "lang_data.xls");

        // Browse rules rulesFileName
        browseOutputDirectoryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Select Output Directory");
                File selectedDirectory = chooser.showDialog(browseOutputDirectoryButton.getScene().getWindow());
                ConfigurationHandler.setOutputDirecotryPath(selectedDirectory.getAbsolutePath() + "/");
                outputDirectoryTextField.setText(ConfigurationHandler.getOutputDirectoryPath());
            }
        });


        // Apply Mandatory Rules
        fixMandatoryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Load text
                LangUtils.fixMandatory(outputDirectoryTextField.getText() + "sin.outtext.txt", ConfigurationHandler.getOutputDirectoryPath());
                logLabel.setText(LangUtils.getLogBrief());
            }
        });


        // Check and fix Ambiguities
        fixAmbiguityButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LangUtils.fixAmbiguity(outputDirectoryTextField.getText() + "sin.outtext.txt", ConfigurationHandler.getOutputDirectoryPath());
                logLabel.setText(LangUtils.getLogBrief());
            }
        });

        // Check legitimacy
        checkLegitimacyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                LangUtils.checkLegitimacy(outputDirectoryTextField.getText() + "sin.outtext.txt", ConfigurationHandler.getOutputDirectoryPath());
//                logLabel.setText(LangUtils.getLogBrief());











            }
        });

        // Check Extended blocks
        checkExBlocksButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LangUtils.checkExBlocks(outputDirectoryTextField.getText() + "sin.outtext.txt", ConfigurationHandler.getOutputDirectoryPath());
                logLabel.setText(LangUtils.getLogBrief());
            }
        });

        // Clear Log
        clearLogButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LangUtils.clearLog(ConfigurationHandler.getOutputDirectoryPath());
                logLabel.setText("");
            }
        });

        // Reload Language Data
        reloadLanguageDataButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                LanguageData.loadLanguageData(tessconfigDir + "lang_data.xls");
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
