package component.general;

import _.LangUtils;
import _.LanguageData;
import common.DiffService;
import common.DiffService.CustomDiff;
import common.Formatter;
import common.ImageService;
import common.OCRService;
import component.Controller;
import configuration.ConfigurationHandler;
import diff.DiffReportService;
import google.DiffMatchPatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GeneralController implements Controller {

    @FXML
    private TextField workspaceTextField;
    @FXML
    private Button changeButton;

    @FXML
    private CheckBox text2imageCheckBox;
    @FXML
    private Button text2imageRunButton;
    @FXML
    private CheckBox ocrCheckBox;
    @FXML
    private Button ocrRunButton;
    @FXML
    private CheckBox comparisonCheckBox;
    @FXML
    private Button comparisonRunButton;
    @FXML
    private CheckBox confusionMatrixCheckBox;
    @FXML
    private Button confuseMatrixRunButton;
    @FXML
    private CheckBox fixMandatoryCheckBox;
    @FXML
    private Button fixMandatoryRunButton;
    @FXML
    private CheckBox fixAmbiguityCheckBox;
    @FXML
    private Button fixAmbiguityRunButton;
    @FXML
    private CheckBox checkLegitimacyCheckBox;
    @FXML
    private Button checkLegitimacyRunButton;

    @FXML
    private Button setTrainedDataButton;

    @FXML
    private ListView<GeneralTask> tasksListView;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button removeAllButton;

    @FXML
    private Button startButton;

    private ObservableList<GeneralTask> generalTasks;

    private String tessconfigDir = "./tessconfig/";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fixMandatoryCheckBox.setSelected(false);
        fixAmbiguityCheckBox.setSelected(false);
        checkLegitimacyCheckBox.setSelected(false);

        generalTasks = FXCollections.observableList(new ArrayList<>());
        tasksListView.setItems(generalTasks);

        // Set previous workspace
        workspaceTextField.setText(ConfigurationHandler.getWorkspacePath());

        // Change workspace
        changeButton.setOnAction(event -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Select workspace");
            File selectedDirectory = chooser.showDialog(changeButton.getScene().getWindow());
            workspaceTextField.setText(selectedDirectory.getAbsolutePath());
            ConfigurationHandler.setWorkspacePath(selectedDirectory.getAbsolutePath());
        });

        // Add task
        addButton.setOnAction(event -> {
            Stage stage = (Stage) addButton.getScene().getWindow();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                GeneralTask generalTask = new GeneralTask(selectedFile.getAbsolutePath());
                generalTask.setName(selectedFile.getName());

                generalTasks.add(generalTask);
            }
        });

        // Remove task
        removeButton.setOnAction(event -> generalTasks.remove(tasksListView.getSelectionModel().getSelectedIndex()));

        // Remove all tasks
        removeAllButton.setOnAction(event -> generalTasks.clear());

        // Set trained data
        // Should select _ folder with an individual file
        setTrainedDataButton.setOnAction(event -> {
            // TODO: 4/7/18  
        });

        // Run Text2Image tasks
        text2imageRunButton.setOnAction(event -> {
            // TODO: 4/7/18  
        });

        // Run OCR tasks
        ocrRunButton.setOnAction(event -> {
            // TODO: 4/7/18  
        });

        // Run Comparison tasks
        comparisonRunButton.setOnAction(event -> {
            // TODO: 4/7/18  
        });

        // Run Confusion Matrix tasks
        confuseMatrixRunButton.setOnAction(event -> {
            // TODO: 4/7/18  
        });

        // Run Apply rules tasks
        fixMandatoryRunButton.setOnAction(event -> {
            // TODO: 4/7/18
        });

        // Run Ambiguities tasks
        fixAmbiguityRunButton.setOnAction(event -> {
            // TODO: 4/7/18  
        });

        // Run Legitimacy tasks
        checkLegitimacyRunButton.setOnAction(event -> {
            // TODO: 4/7/18  
        });

        startButton.setOnAction(event -> {
            for (GeneralTask generalTask : generalTasks) {
                String outputDirectoryPath = Formatter.formatOutputDirectory(workspaceTextField.getText(), generalTask.getName());

                // Create the output directory if it doesn't exist
                File outputDirectory = new File(outputDirectoryPath);
                if (!outputDirectory.exists()) {
                    outputDirectory.mkdir();
                }

                // Copy the input file to output directory
                try {
                    FileUtils.copyFile(new File(generalTask.getInputPath()), new File(outputDirectoryPath + "/input.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (text2imageCheckBox.isSelected()) {
                    ImageService.text2ImageDocker(generalTask.getInputPath(), outputDirectoryPath + "/out");
                }

                if (ocrCheckBox.isSelected()) {
                    OCRService.ocrDocker(outputDirectoryPath + "out.tif", outputDirectoryPath + "/output");
                }

                if (comparisonCheckBox.isSelected()) {
                    try {
                        List<CustomDiff> deltas = DiffService.getDefaultDiff(outputDirectoryPath);
                        DiffReportService.generateDefault(deltas, outputDirectoryPath, "diff_google");

                        DiffService.formatDiff(deltas);
                        DiffReportService.generateDefault(deltas, outputDirectoryPath, "diff_formatted");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (confusionMatrixCheckBox.isSelected()) {
                    // TODO: 4/7/18
                }

                // Preparing for Post Process
                LanguageData.loadLanguageData(tessconfigDir + "lang_data.xls");

                if (fixAmbiguityCheckBox.isSelected()) {
                    LangUtils.fixAmbiguity(outputDirectoryPath + "sin.outtext.txt", outputDirectoryPath);
                }

                if (fixMandatoryCheckBox.isSelected()) {
                    LangUtils.fixMandatory(outputDirectoryPath + "sin.outtext.txt", outputDirectoryPath);
                }

                if (checkLegitimacyCheckBox.isSelected()) {
                    LangUtils.checkLegitimacy(outputDirectoryPath + "sin.outtext.txt", outputDirectoryPath);
                }
            }

            System.exit(0);
        });
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onRefresh() {

    }
}
