package component;

import javafx.scene.Parent;

public class Component {

    private Parent parent;
    private Controller controller;

    public Component() {
    }

    Component(Parent parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
