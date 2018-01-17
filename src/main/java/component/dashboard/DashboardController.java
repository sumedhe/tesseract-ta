package component.dashboard;

import component.Controller;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Controller {

    @FXML
    private AnchorPane workspaceAnchorPane;
    @FXML
    private Button ocrButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onRefresh() {

    }

    public void addParent(Parent parent){
        workspaceAnchorPane.getChildren().addAll(parent);
    }

//    private void slideInParent(Parent parent) {
//        TranslateTransition parentInTranslation = new TranslateTransition(WORKSPACE_ANIMATE_TIME);
//        parentInTranslation.setFromX(1100);
//        parentInTranslation.setToX(0);
//        parentInTranslation.setNode(parent);
//
//        if (workspaceAnchorPane.getChildren().size() == 0) {
//            workspaceAnchorPane.getChildren().add(parent);
//            parentInTranslation.play();
//        } else {
//            Node childNode = workspaceAnchorPane.getChildren().get(0);
//            workspaceAnchorPane.getChildren().add(parent);
//
//            TranslateTransition childOutTranslation = new TranslateTransition(WORKSPACE_ANIMATE_TIME);
//            childOutTranslation.setFromX(0);
//            childOutTranslation.setToX(-1100);
//            childOutTranslation.setNode(childNode);
//
//            ParallelTransition parallelTransition = new ParallelTransition(parentInTranslation, childOutTranslation);
//            parallelTransition.setOnFinished(event -> {
//                Iterator<Node> nodeIterator = workspaceAnchorPane.getChildren().iterator();
//                while (nodeIterator.hasNext()) {
//                    nodeIterator.next();
//                    if (nodeIterator.hasNext()) {
//                        nodeIterator.remove();
//                    }
//                }
//            });
//            parallelTransition.play();
//        }
//    }

//    private void slideOutChildren() {
//        Iterator<Node> nodeIterator = this.workspaceAnchorPane.getChildren().iterator();
//        while (nodeIterator.hasNext()) {
//            TranslateTransition translateTransition = new TranslateTransition(WORKSPACE_ANIMATE_TIME);
//            translateTransition.setFromX(0);
//            translateTransition.setToX(1100);
//            translateTransition.setNode(nodeIterator.next());
//            translateTransition.setOnFinished(event -> nodeIterator.remove());
//            translateTransition.play();
//        }
//    }
}
