package component.dashboard;

import component.ComponentFactory;
import component.Controller;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class DashboardController implements Controller {

    @FXML
    private AnchorPane workspaceAnchorPane;
    @FXML
    private Button ocrButton;
    @FXML
    private Button confusionMatrixButton;
    @FXML
    private Button dictionaryButton;
    @FXML
    private Button grammarCheckButton;
    @FXML
    private Button settingsButton;

    private static final Duration WORKSPACE_ANIMATE_TIME = Duration.millis(400);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ocrButton.setOnAction(event -> DashboardController.this.setWorkspace(ComponentFactory.getComponent(ComponentFactory.ComponentName.OCR_COMPONENT).getParent()));

        confusionMatrixButton.setOnAction(event -> DashboardController.this.setWorkspace(ComponentFactory.getComponent(ComponentFactory.ComponentName.CONFUSION_MATRIX_COMPONENT).getParent()));

        dictionaryButton.setOnAction(event -> DashboardController.this.setWorkspace(ComponentFactory.getComponent(ComponentFactory.ComponentName.DICTIONARY_COMPONENT).getParent()));

        grammarCheckButton.setOnAction(event -> DashboardController.this.setWorkspace(ComponentFactory.getComponent(ComponentFactory.ComponentName.GRAMMAR_CHECK_COMPONENT).getParent()));

        settingsButton.setOnAction(event -> DashboardController.this.setWorkspace(ComponentFactory.getComponent(ComponentFactory.ComponentName.SETTINGS_COMPONENT).getParent()));

        Rectangle clipRectangle = new Rectangle(950, 675);
        workspaceAnchorPane.setClip(clipRectangle);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onRefresh() {

    }

    public void setWorkspace(Parent parent) {
        if (!(workspaceAnchorPane.getChildren().size() > 0 && workspaceAnchorPane.getChildren().get(0).equals(parent))) {
            slideInParent(parent);
        }
    }

    private void slideInParent(Parent parent) {
        TranslateTransition parentInTranslation = new TranslateTransition(WORKSPACE_ANIMATE_TIME);
        parentInTranslation.setFromX(950);
        parentInTranslation.setToX(0);
        parentInTranslation.setNode(parent);

        if (workspaceAnchorPane.getChildren().size() == 0) {
            workspaceAnchorPane.getChildren().add(parent);
            parentInTranslation.play();
        } else {
            Node childNode = workspaceAnchorPane.getChildren().get(0);
            workspaceAnchorPane.getChildren().add(parent);

            TranslateTransition childOutTranslation = new TranslateTransition(WORKSPACE_ANIMATE_TIME);
            childOutTranslation.setFromX(0);
            childOutTranslation.setToX(-950);
            childOutTranslation.setNode(childNode);

            ParallelTransition parallelTransition = new ParallelTransition(parentInTranslation, childOutTranslation);
            parallelTransition.setOnFinished(event -> {
                Iterator<Node> nodeIterator = workspaceAnchorPane.getChildren().iterator();
                while (nodeIterator.hasNext()) {
                    nodeIterator.next();
                    if (nodeIterator.hasNext()) {
                        nodeIterator.remove();
                    }
                }
            });
            parallelTransition.play();
        }
    }
}
