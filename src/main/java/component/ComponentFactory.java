package component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.HashMap;

import static component.ComponentName.DASHBOARD_COMPONENT;
import static component.ComponentName.OCR_COMPONENT;
import static component.ComponentName.SETTINGS_COMPONENT;

public class ComponentFactory {
    private static final HashMap<ComponentName, Component> COMPONENT_HASH_MAP = new HashMap<>();

    public ComponentFactory() {
    }

    public static Component getComponent(ComponentName componentName) {
        try {
            switch (componentName) {
                case DASHBOARD_COMPONENT:
                    initialize(DASHBOARD_COMPONENT, "/component/dashboard/dashboard.fxml");
                    break;
                case OCR_COMPONENT:
                    initialize(OCR_COMPONENT, "/component/dashboard/dashboard.fxml");
                    break;
                case SETTINGS_COMPONENT:
                    initialize(SETTINGS_COMPONENT, "/component/dashboard/dashboard.fxml");
                    break;
                default:
                    initialize(DASHBOARD_COMPONENT, "/component/dashboard/dashboard.fxml");
                    break;
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        return COMPONENT_HASH_MAP.get(componentName);
    }

    private static void initialize(ComponentName componentName, String fxmlPath) throws IOException {
        if(!COMPONENT_HASH_MAP.containsKey(componentName)){
            FXMLLoader loader = new FXMLLoader(ComponentFactory.class.getResource(fxmlPath));
            Parent parent = loader.load();
            Controller controller = loader.getController();
            Component component = new Component(parent, controller);
            COMPONENT_HASH_MAP.put(componentName, component);
        }
    }
}
