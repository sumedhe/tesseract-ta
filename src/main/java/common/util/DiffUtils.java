package common.util;

import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.Delta;
import com.github.difflib.patch.Patch;
import com.opencsv.CSVWriter;
import common.googlediff.DiffMatchPatch;
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

public class DiffUtils {

    public static void diffUtilsLib(String outputDirectoryPath) throws IOException, DiffException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Diff analysis - Utils lib");
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 10000);
        sheet.setColumnWidth(3, 10000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Type");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Index");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Original");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Revised");
        headerCell.setCellStyle(headerStyle);

        byte[] encoded = Files.readAllBytes(Paths.get(outputDirectoryPath + "/input.txt"));
        String s1 = new String(encoded, Charset.defaultCharset());
        String[] s1array = s1.split("[,\\s]");
        List<String> inputText = Arrays.asList(s1array);

        byte[] encoded2 = Files.readAllBytes(Paths.get(outputDirectoryPath + "/output.txt"));
        String s2 = new String(encoded2, Charset.defaultCharset());
        String[] s2array = s2.split("[,\\s]");
        List<String> outputText = Arrays.asList(s2array);

        Patch<String> patch = com.github.difflib.DiffUtils.diff(inputText, outputText);

        int i =  2;
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        for (Delta delta : patch.getDeltas()) {
            Row row = sheet.createRow(i++);
            Cell cell = row.createCell(0);
            cell.setCellValue(delta.getType().toString());

            cell = row.createCell(1);
            cell.setCellValue(delta.getOriginal().getPosition());

            cell = row.createCell(2);
            cell.setCellValue(delta.getOriginal().getLines().toString());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(delta.getRevised().getLines().toString());
            cell.setCellStyle(style);
        }

        FileOutputStream outputStream = new FileOutputStream(outputDirectoryPath + "/diff_utilslib.xlsx");
        workbook.write(outputStream);
        workbook.close();
    }

    public static void diffGoogle(String outputDirectoryPath) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Diff analysis - Google");
        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Input");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Output");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Type");
        headerCell.setCellStyle(headerStyle);

        byte[] encoded = Files.readAllBytes(Paths.get(outputDirectoryPath + "/input.txt"));
        String s1 = new String(encoded, Charset.defaultCharset());

        byte[] encoded2 = Files.readAllBytes(Paths.get(outputDirectoryPath + "/output.txt"));
        String s2 = new String(encoded2, Charset.defaultCharset());

        DiffMatchPatch difference = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> deltas = difference.diff_main(s1, s2);

        int i =  2;
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(false);
        for (DiffMatchPatch.Diff d : deltas) {
            Row row = sheet.createRow(i++);
            if (d.operation == DiffMatchPatch.Operation.EQUAL) {
                Cell cell = row.createCell(0);
                cell.setCellValue("[" + d.text + "]");
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue("[" + d.text + "]");
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue("Equal");
                cell.setCellStyle(style);
            }
            else if (d.operation == DiffMatchPatch.Operation.INSERT){
                Cell cell = row.createCell(0);
                cell.setCellValue("[" + d.text + "]");
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue("[]");
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue("Insert");
                cell.setCellStyle(style);
            }
            else if (d.operation == DiffMatchPatch.Operation.DELETE){
                Cell cell = row.createCell(0);
                cell.setCellValue("[]");
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue("[" + d.text + "]");
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue("Delete");
                cell.setCellStyle(style);
            }
        }

        FileOutputStream outputStream = new FileOutputStream(outputDirectoryPath + "/diff_google.xlsx");
        workbook.write(outputStream);
        workbook.close();

    }

    public static void comparison(String outputDirectoryPath) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Comparison");
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 20000);
        sheet.setColumnWidth(2, 20000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Index");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Input");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Output");
        headerCell.setCellStyle(headerStyle);

        List<String> original = Files.readAllLines(Paths.get(outputDirectoryPath + "/input.txt"));
        List<String> revised = Files.readAllLines(Paths.get(outputDirectoryPath + "/output.txt"));

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        int rowNum = 2;
        for(int i = 0; i < Math.max(original.size(), revised.size()); i++){
            Row row = sheet.createRow(rowNum++);
        }

        rowNum = 2;
        for(int i = 0; i < original.size(); i++){
            Row row = sheet.getRow(rowNum++);
            Cell cell = row.createCell(0);
            cell.setCellValue(i);

            cell = row.createCell(1);
            cell.setCellValue(original.get(i));
        }

        rowNum = 2;
        for(int i = 0; i < revised.size(); i++){
            Row row = sheet.getRow(rowNum++);
            Cell cell = row.createCell(2);
            cell.setCellValue(revised.get(i));
        }

        FileOutputStream outputStream = new FileOutputStream(outputDirectoryPath + "/line_comparison.xlsx");
        workbook.write(outputStream);
        workbook.close();
    }

    public static void diff(String outputDirectoryPath){

    }
}
