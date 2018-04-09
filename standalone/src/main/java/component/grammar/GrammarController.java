package component.grammar;

import common.GrammarService;
import component.Controller;
import configuration.ConfigurationHandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import rules.AmbiguousRules;
import rules.LegitimacyRules;
import rules.MandatoryRules;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GrammarController implements Controller {


    private final String tessconfigDir = "./tessconfig/";
    @FXML
    private TextField outputDirectoryTextField;
    @FXML
    private Button browseOutputDirectoryButton;
    @FXML
    private Button clearLogButton;
    @FXML
    private Button reloadLanguageDataButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set previous filenames
        outputDirectoryTextField.setText(ConfigurationHandler.getOutputDirectoryPath());

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

        // Clear Log
        clearLogButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GrammarService.execute("/home/sumedhe/Documents/tmp/OCRSample/sin.outtext.txt", "/home/sumedhe/Documents/tmp/OCRSample/report.html");
            }
        });

        // Reload Language Data
        reloadLanguageDataButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                AmbiguousRules.load();
                MandatoryRules.load();
                LegitimacyRules.load();
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
