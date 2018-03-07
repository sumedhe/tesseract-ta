package component.dictionary;

import common.ExcelLoader;
import component.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class DictionaryController implements Controller{
    @FXML
    private TextArea contentTextArea;

    private Tooltip tooltip;

    @FXML
    private Button browseButton;

    String RuleArray[][];

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

        browseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                applyRules();
            }
        });


    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onRefresh() {

    }

    // Apply Rules
    public void applyRules(){
        // Load Rules
        ExcelLoader excelLoader = new ExcelLoader("rules.xls");
        String[][] rules = excelLoader.loadData();
        for (String[] ss : rules){
            System.out.println(ss[0] + " " + ss[1]);
        }

        // Load text
        String contentText = contentTextArea.getText();

        // Find and fix
        for (String[] rule: rules){
            if (rule[0] != null && rule[1] != null){
                contentText = contentText.replaceAll(rule[0], rule[1]);
            }
        }

        contentTextArea.setText(contentText);
    }

}
