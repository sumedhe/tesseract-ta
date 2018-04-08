package component;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public interface Controller extends Initializable {

    @Override
    void initialize(URL location, ResourceBundle resources);

    void onLoad();

    void onRefresh();
}