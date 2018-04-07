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
        GENERAL_COMPONENT,
        OCR_COMPONENT,
        COMPARISON_COMPONENT,
        CONFUSION_MATRIX_COMPONENT,
        DICTIONARY_COMPONENT,
        GRAMMAR_CHECK_COMPONENT,
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
                case GENERAL_COMPONENT:
                    initialize(GENERAL_COMPONENT, "/component/general/general.fxml");
                    break;
                case OCR_COMPONENT:
                    initialize(OCR_COMPONENT, "/component/ocr/ocr.fxml");
                    break;
                case COMPARISON_COMPONENT:
                    initialize(COMPARISON_COMPONENT, "/component/comparison/comparison.fxml");
                    break;
                case CONFUSION_MATRIX_COMPONENT:
                    initialize(CONFUSION_MATRIX_COMPONENT, "/component/confusionmatrix/confusionMatrix.fxml");
                    break;
                case DICTIONARY_COMPONENT:
                    initialize(DICTIONARY_COMPONENT, "/component/dictionary/dictionary.fxml");
                    break;
                case GRAMMAR_CHECK_COMPONENT:
                    initialize(GRAMMAR_CHECK_COMPONENT, "/component/grammarcheck/grammarCheck.fxml");
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
