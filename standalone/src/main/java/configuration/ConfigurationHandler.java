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


    public static void setOutputDirecotryPath(String outputPath) {
        configurationFile.setProperty("output_directory_path", outputPath);
    }

    public static String getOutputDirectoryPath() {
        return configurationFile.getProperty("output_directory_path");
    }

    public static void setDefaultConfig() {
        configurationFile.setDefaultConfigurations();
    }
}
