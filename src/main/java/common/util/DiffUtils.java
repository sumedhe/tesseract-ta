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
    public static void diff(String outputDirectoryPath){
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(outputDirectoryPath + "/input.txt"));
            String s1 = new String(encoded, Charset.defaultCharset());

            byte[] encoded2 = Files.readAllBytes(Paths.get(outputDirectoryPath + "/output.txt"));
            String s2 = new String(encoded2, Charset.defaultCharset());

            DiffMatchPatch difference = new DiffMatchPatch();
            LinkedList<DiffMatchPatch.Diff> deltas = difference.diff_main(s1, s2);

            try (
                    Writer writer = Files.newBufferedWriter(Paths.get(outputDirectoryPath + "/diff_google.csv"));

                    CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            ) {
                String[] headerRecord = {"C1", "C2", "Equality"};
                csvWriter.writeNext(headerRecord);

                for (DiffMatchPatch.Diff d : deltas) {
                    if (d.operation == DiffMatchPatch.Operation.EQUAL) {
                        csvWriter.writeNext(new String[]{d.text.replace("\n", "").replace("\r", ""),
                                d.text.replace("\n", "").replace("\r", ""), "Equal"});
                    }
                    else if (d.operation == DiffMatchPatch.Operation.INSERT){
                        csvWriter.writeNext(new String[]{d.text.replace("\n", "").replace("\r", ""), "", "Insert"});
                    }
                    else if (d.operation == DiffMatchPatch.Operation.DELETE){
                        csvWriter.writeNext(new String[]{"", d.text.replace("\n", "").replace("\r", ""), "Delete"});
                    }
                }
            }

            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Diff analysis");
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

            String[] s1array = s1.split("[,\\s]");
            String[] s2array = s2.split("[,\\s]");

            List<String> original = Arrays.asList(s1array);
            List<String> revised = Arrays.asList(s2array);

            Patch<String> patch = com.github.difflib.DiffUtils.diff(original, revised);

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

            FileOutputStream outputStream = new FileOutputStream(outputDirectoryPath + "/diff.xlsx");
            workbook.write(outputStream);
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DiffException e) {
            e.printStackTrace();
        }
    }
}
