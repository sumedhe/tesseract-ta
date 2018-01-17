package component.ocr;

import component.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ResourceBundle;

public class OCRController implements Controller {

    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button startButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
//                File selectedFile = fileChooser.showOpenDialog(mainStage);
//                if (selectedFile != null) {
//                    mainStage.display(selectedFile);
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
