package configuration;

public class ConfigurationFile {
    private ConfigurationHandler configurationHandler;

    private String stageTitle;

    public ConfigurationFile(ConfigurationHandler configurationHandler) {
        this.configurationHandler = configurationHandler;

        this.stageTitle = configurationHandler.getProperty("stage_title");
    }

    public String getStageTitle() {
        return stageTitle;
    }

    public void setStageTitle(String stageTitle) {
        this.stageTitle = stageTitle;
        configurationHandler.setProperty("stage_title", stageTitle);
    }

    public void setDefaultConfig() {
        configurationHandler.setDefaultConfigurations();
    }
}
