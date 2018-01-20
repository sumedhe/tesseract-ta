package component.ocr;

import common.Shell;
import component.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OCRController implements Controller {

    @FXML
    private ListView<OCRTask> tasksListView;
    @FXML
    private Button addButton;
    @FXML
    private Button startButton;

    private ObservableList<OCRTask> ocrTasks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ocrTasks = FXCollections.observableList(new ArrayList<OCRTask>());
        tasksListView.setItems(ocrTasks);

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

                Shell s = new Shell("ls");
//                s.execute();
                s.setCommand("cd " + ocrTask.getInputPath().substring(0, ocrTask.getInputPath().lastIndexOf(File.separator)) + "/");
                s.execute();
//                s.setCommand("text2image " +
//                        "--text sin.testtext.txt " +
//                        "--outputbase sin.testtext " +
//                        "--fonts_dir " + System.getProperty("user.dir") + "/tessdata" + " " +
//                        "--font \"Iskoola Pota Bold\"");
//                s.execute();
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
