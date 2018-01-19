package component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.HashMap;

import static component.ComponentFactory.ComponentName.*;

public class ComponentFactory {
    private static final HashMap<ComponentName, Component> COMPONENT_HASH_MAP = new HashMap<>();

    public enum ComponentName {
        DASHBOARD_COMPONENT,
        OCR_COMPONENT,
        SETTINGS_COMPONENT;
    }

    public ComponentFactory() {
    }

    public static Component getComponent(ComponentName componentName) {
        try {
            switch (componentName) {
                case DASHBOARD_COMPONENT:
                    initialize(DASHBOARD_COMPONENT, "/component/dashboard/dashboard.fxml");
                    break;
                case OCR_COMPONENT:
                    initialize(OCR_COMPONENT, "/component/ocr/ocr.fxml");
                    break;
                case SETTINGS_COMPONENT:
                    initialize(SETTINGS_COMPONENT, "/component/settings/settings.fxml");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return COMPONENT_HASH_MAP.get(componentName);
    }

    private static void initialize(ComponentName componentName, String fxmlPath) throws IOException {
        if (!COMPONENT_HASH_MAP.containsKey(componentName)) {
            FXMLLoader loader = new FXMLLoader(ComponentFactory.class.getResource(fxmlPath));
            Parent parent = loader.load();
            Controller controller = loader.getController();
            Component component = new Component(parent, controller);
            COMPONENT_HASH_MAP.put(componentName, component);
        }
    }
}
