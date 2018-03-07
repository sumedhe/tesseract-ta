package component.dictionary;

import component.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryController implements Controller{
    @FXML
    private TextArea contentTextArea;

    private Tooltip tooltip;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tooltip = new Tooltip();
        tooltip.autoHideProperty().setValue(true);
        contentTextArea.setTooltip(tooltip);

        contentTextArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MouseButton button = mouseEvent.getButton();
                if (button == MouseButton.MIDDLE || (button == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2)){
                    String selectedText = contentTextArea.getSelectedText();
                    String unicodeString = "";
                    for (char c: selectedText.toCharArray()){
                        unicodeString += "\\u" + Integer.toHexString(c | 0x10000).substring(1) + ": " + c + "\n";
                    }

                    tooltip.setText(unicodeString);
                    tooltip.show(contentTextArea, mouseEvent.getScreenX(), mouseEvent.getScreenY());
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
