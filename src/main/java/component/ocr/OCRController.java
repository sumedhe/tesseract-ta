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
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;

import java.io.File;
import java.io.IOException;
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

                String inputPath = ocrTask.getInputPath().substring(0, ocrTask.getInputPath().lastIndexOf(File.separator)) + "/";

//                System.out.println(ocrTask.getInputPath().substring(0, ocrTask.getInputPath().lastIndexOf(File.separator)));

                Shell s = new Shell("ls");
                s.run();
                s.setCommand("text2image " +
                        "--text " + ocrTask.getInputPath() + " " +
                        "--outputbase " + ocrTask.getInputPath().substring(0, ocrTask.getInputPath().lastIndexOf(File.separator)) + "/sin.testtext " +
                        "--fonts_dir " + getClass().getClassLoader().getResource("tessdata").getPath() + " " +
                        "--font \"Iskoola Pota\"");
                s.execute();

//                String c = "text2image " +
//                        "--text " + ocrTask.getInputPath() + " " +
//                        "--outputbase " + ocrTask.getInputPath().substring(0, ocrTask.getInputPath().lastIndexOf(File.separator)) + "/sin.testtext " +
//                        "--fonts_dir " + getClass().getClassLoader().getResource("tessdata").getPath() + " " +
//                        "--font \"Iskoola Pota\"";
//                System.out.println(c);
//                CommandLine cmdLine = CommandLine.parse(c);
//                DefaultExecutor executor = new DefaultExecutor();
//                try {
//                    int exitValue = executor.execute(cmdLine);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(c);
//                CommandLine cmdLine = new CommandLine("text2image");
//                cmdLine.addArgument("--text " + ocrTask.getInputPath());
//                cmdLine.addArgument("--outputbase " + ocrTask.getInputPath().substring(0, ocrTask.getInputPath().lastIndexOf(File.separator)) + "/sin.testtext");
//                cmdLine.addArgument("--fonts_dir " + getClass().getClassLoader().getResource("tessdata").getPath());
//                cmdLine.addArgument("--font \"Iskoola Pota\"");
//                DefaultExecutor executor = new DefaultExecutor();
//                executor.setExitValue(1);
//                ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
//                executor.setWatchdog(watchdog);
//                try {
//                    int exitValue = executor.execute(cmdLine);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
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
