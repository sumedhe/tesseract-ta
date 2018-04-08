import component.ComponentFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import static component.ComponentFactory.ComponentName.DASHBOARD_COMPONENT;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(ComponentFactory.getComponent(DASHBOARD_COMPONENT).getParent());
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });

        primaryStage.setTitle("Tessaract Test Automation System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
