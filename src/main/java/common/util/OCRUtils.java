package common.util;

import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.Delta;
import com.github.difflib.patch.Patch;
import com.opencsv.CSVWriter;
import common.googlediff.DiffMatchPatch;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
