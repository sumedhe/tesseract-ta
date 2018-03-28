package component.ocr;

import com.github.difflib.algorithm.DiffException;
import common.Formatter;
import common.util.OCRUtils;
import common.util.DiffUtils;
import common.util.ImageUtils;
import component.Controller;
import configuration.ConfigurationHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OCRController implements Controller {

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
    private CheckBox applyRulesCheckBox;
    @FXML
    private Button applyRulesRunButton;
    @FXML
    private CheckBox ambiguitiesCheckBox;
    @FXML
    private Button ambiguitiesRunButton;
    @FXML
    private CheckBox legitimacyCheckBox;
    @FXML
    private Button legitimacyRunButton;

    @FXML
    private Button setTrainedDataButton;

    @FXML
    private ListView<OCRTask> tasksListView;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button removeAllButton;

    @FXML
    private Button startButton;

    private ObservableList<OCRTask> ocrTasks;

    private String tessdataDir = "./";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ocrTasks = FXCollections.observableList(new ArrayList<OCRTask>());
        tasksListView.setItems(ocrTasks);

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
                OCRTask ocrTask = new OCRTask(selectedFile.getAbsolutePath());
                ocrTask.setName(selectedFile.getName());

                ocrTasks.add(ocrTask);
            }
        });

        // Remove task
        removeButton.setOnAction(event -> ocrTasks.remove(tasksListView.getSelectionModel().getSelectedIndex()));

        // Remove all tasks
        removeAllButton.setOnAction(event -> ocrTasks.clear());

        // Set trained data
        // Should select a folder with an individual file
        setTrainedDataButton.setOnAction(event -> {
            Stage stage = (Stage) setTrainedDataButton.getScene().getWindow();

            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Open Trained-data Directory");
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null && new File(selectedDirectory, "sin.traineddata").exists()) {
                tessdataDir = selectedDirectory.getAbsolutePath();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The directory should contain a sin.traineddata file", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();
            }
        });

        // Run Text2Image tasks
        text2imageRunButton.setOnAction(event -> {
            // To do
            System.out.println("Text2Image");
        });

        // Run OCR tasks
        ocrRunButton.setOnAction(event -> {
            // To do
            System.out.println("OCR");
        });

        // Run Comparison tasks
        comparisonRunButton.setOnAction(event -> {
            // To do
            System.out.println("Comparison");
        });

        // Run Confusion Matrix tasks
        confuseMatrixRunButton.setOnAction(event -> {
            // To do
            System.out.println("Confusion Matrix");
        });

        // Run Apply rules tasks
        applyRulesRunButton.setOnAction(event -> {
            // To do
            System.out.println("Apply rules");
        });

        // Run Ambiguities tasks
        ambiguitiesRunButton.setOnAction(event -> {
            // To do
            System.out.println("Ambiguities");
        });

        // Run Legitimacy tasks
        legitimacyRunButton.setOnAction(event -> {
            // To do
            System.out.println("Legitimacy");
        });

        startButton.setOnAction(event -> {
            for (OCRTask ocrTask : ocrTasks) {
                String outputDirectoryPath = Formatter.formatOutputDirectory(workspaceTextField.getText(), ocrTask.getName());

                // Create the output directory if it doesn't exist
                File outputDirectory = new File(outputDirectoryPath);
                if (!outputDirectory.exists()) {
                    outputDirectory.mkdir();
                }

                // Copy the input file to output directory
                try {
                    FileUtils.copyFile(new File(ocrTask.getInputPath()), new File(outputDirectoryPath + "/input.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (text2imageCheckBox.isSelected()) {
                    ImageUtils.text2Image(ocrTask.getInputPath(), outputDirectoryPath + "/out");
                }

                if (ocrCheckBox.isSelected()) {
                    OCRUtils.ocr(outputDirectoryPath + "out.tif", outputDirectoryPath + "/output", tessdataDir);
                }

                if (comparisonCheckBox.isSelected()) {
                    try {
                        DiffUtils.diffGoogle(outputDirectoryPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        DiffUtils.diffUtilsLib(outputDirectoryPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DiffException e) {
                        e.printStackTrace();
                    }

                    try {
                        DiffUtils.comparison(outputDirectoryPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DiffUtils.diff(outputDirectoryPath);
                }

                if (confusionMatrixCheckBox.isSelected()) {

                }

                if (applyRulesCheckBox.isSelected()) {

                }

                if (ambiguitiesCheckBox.isSelected()) {

                }

                if (legitimacyCheckBox.isSelected()) {

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
