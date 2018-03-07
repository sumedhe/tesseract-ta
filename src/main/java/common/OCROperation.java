package common;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.environment.EnvironmentUtils;

import java.io.IOException;
import java.util.Map;

public class OCROperation {

    public static void text2Image(String inputPath, String outputPath){
        CommandLine cmdLine = new CommandLine("text2image");
        cmdLine.addArgument("--text");
        cmdLine.addArgument(inputPath);
        cmdLine.addArgument("--outputbase");
        cmdLine.addArgument(outputPath);
        cmdLine.addArgument("--fonts_dir");
        cmdLine.addArgument("/Users/ivantha/Git/tessaract-ta/tessdata");
        cmdLine.addArgument("--font");
        cmdLine.addArgument("Iskoola Pota", false);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(0);

        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
        executor.setWatchdog(watchdog);

        Map<String, String> customEnvironment = null;
        try {
            customEnvironment = EnvironmentUtils.getProcEnvironment();
        } catch (IOException e) {
            e.printStackTrace();
        }
        customEnvironment.put("PANGOCAIRO_BACKEND", "fc");

        try {
            int exitValue = executor.execute(cmdLine, customEnvironment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ocr(String inputPath, String outputPath){
        CommandLine cmdLine = new CommandLine("tesseract");
        cmdLine.addArgument("--tessdata-dir");
        cmdLine.addArgument("./");
        cmdLine.addArgument(inputPath);
        cmdLine.addArgument(outputPath);
        cmdLine.addArgument("-l");
        cmdLine.addArgument("sin");
        cmdLine.addArgument("segdemo");
        cmdLine.addArgument("inter");

        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(0);

        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
        executor.setWatchdog(watchdog);

        Map<String, String> customEnvironment = null;
        try {
            customEnvironment = EnvironmentUtils.getProcEnvironment();
        } catch (IOException e) {
            e.printStackTrace();
        }
        customEnvironment.put("PANGOCAIRO_BACKEND", "fc");

        try {
            int exitValue = executor.execute(cmdLine, customEnvironment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
