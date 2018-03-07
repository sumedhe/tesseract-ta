package component.ocr;

import org.apache.commons.io.FilenameUtils;

public class OCRTask {
    private String id;
    private String name;
    private String inputPath;
    private String outputPath;
    private double progress;

    public OCRTask(String inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public String toString() {
        return inputPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return FilenameUtils.removeExtension(name);
    }

    public String getNameWithExtension(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
