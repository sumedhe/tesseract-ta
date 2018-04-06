package common.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.environment.EnvironmentUtils;

import java.io.IOException;
import java.util.Map;

public class OCRUtils {

    public static void ocr(String inputPath, String outputPath, String tessdataDir){
        CommandLine cmdLine = new CommandLine("tesseract");
        cmdLine.addArgument("--tessdata-dir");
        cmdLine.addArgument(tessdataDir);
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
