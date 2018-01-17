import component.ComponentFactory;
import component.ComponentName;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class App extends Application{
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(ComponentFactory.getComponent(ComponentName.DASHBOARD_COMPONENT).getParent());
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });

        primaryStage.setTitle("Tessaract Test Automation System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
