package configuration;

public class ConfigurationHandler {
    private static ConfigurationFile configurationFile = new ConfigurationFile();

    public ConfigurationHandler() {
        ConfigurationHandler.configurationFile = new ConfigurationFile();
    }

    public static String getStageTitle() {
        return configurationFile.getProperty("stage_title");
    }

    public static void setStageTitle(String stageTitle) {
        configurationFile.setProperty("stage_title", stageTitle);
    }

    public static String getWorkspacePath() {
        return configurationFile.getProperty("workspace_path");
    }

    public static void setWorkspacePath(String workspacePath) {
        configurationFile.setProperty("workspace_path", workspacePath);
    }


    public static void setOutputTextPath(String outputPath){
        configurationFile.setProperty("output_text_path", outputPath);
    }

    public static String getOutputTextPath(){
        return configurationFile.getProperty("output_text_path");
    }

    public static void setDefaultConfig() {
        configurationFile.setDefaultConfigurations();
    }
}
