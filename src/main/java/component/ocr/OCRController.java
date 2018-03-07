package component.ocr;

import common.Formatter;
import common.OCROperation;
import component.Controller;
import configuration.ConfigurationHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private CheckBox dictionaryCheckBox;
    @FXML
    private Button dictionaryRunButton;
    @FXML
    private CheckBox grammarCheckCheckBox;
    @FXML
    private Button grammarCheckRunButton;
    @FXML
    private CheckBox confusionMatrixCheckBox;
    @FXML
    private Button confuseMatrixRunButton;

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

        // Run Dictionary tasks
        dictionaryRunButton.setOnAction(event -> {
            // To do
            System.out.println("Dictionary");
        });

        // Run Grammar tasks
        grammarCheckRunButton.setOnAction(event -> {
            // To do
            System.out.println("Grammar");
        });

        // Run Confusion Matrix tasks
        confuseMatrixRunButton.setOnAction(event -> {
            // To do
            System.out.println("Confusion Matrix");
        });

        startButton.setOnAction(event -> {
            for(OCRTask ocrTask: ocrTasks){
                String outputDirectoryPath = Formatter.formatOutputDirectory(workspaceTextField.getText(), ocrTask.getName());

                // Create the output directory if it doesn't exist
                File file = new File(outputDirectoryPath);
                if (!file.exists()) {
                    file.mkdir();
                }

                // Copy the input file to output directory
                try {
                    FileUtils.copyFile(new File(ocrTask.getInputPath()), new File(outputDirectoryPath + "/input.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(text2imageCheckBox.isSelected()){
                    OCROperation.text2Image(ocrTask.getInputPath(),outputDirectoryPath + "/out");
                }

                if(ocrCheckBox.isSelected()){
                    OCROperation.ocr(outputDirectoryPath + "out.tif",outputDirectoryPath + "/output");
                }

                if(comparisonCheckBox.isSelected()){

                }

                if(dictionaryCheckBox.isSelected()){

                }

                if(grammarCheckCheckBox.isSelected()){

                }

                if(confusionMatrixCheckBox.isSelected()){

                }
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
