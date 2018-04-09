package diff;

import common.DiffService.CustomDiff;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DiffReportService {

    public static void generateDefault(List<CustomDiff> deltas, String outputDirectoryPath, String outputFileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerCellStyle = workbook.createCellStyle();

        CellStyle defaultCellStyle = workbook.createCellStyle();
        defaultCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        defaultCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        defaultCellStyle.setWrapText(true);

        CellStyle deleteCellStyle = workbook.createCellStyle();
        deleteCellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        deleteCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        deleteCellStyle.setWrapText(true);

        CellStyle insertCellStyle = workbook.createCellStyle();
        insertCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        insertCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        insertCellStyle.setWrapText(true);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerCellStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Input text");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("OCR output");
        headerCell.setCellStyle(headerCellStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Type");
        headerCell.setCellStyle(headerCellStyle);

        int i = 1;
        for (CustomDiff d : deltas) {
            Row row = sheet.createRow(i++);

            d.text = d.text
                    .replace("¶", "<p>")
                    .replace("\n", "<n>")
                    .replace("\t", "<t>")
                    .replace("\r", "<r>")
                    .replace("\b", "<b>")
                    .replace(" ", "<s>");

            switch (d.operation) {
                case EQUAL: {
                    Cell cell = row.createCell(0);
                    cell.setCellValue(d.text);
                    cell.setCellStyle(defaultCellStyle);

                    cell = row.createCell(1);
                    cell.setCellValue(d.text);
                    cell.setCellStyle(defaultCellStyle);

                    cell = row.createCell(2);
                    cell.setCellValue(d.description);
                    cell.setCellStyle(defaultCellStyle);
                    break;
                }
                case INSERT: {
                    Cell cell = row.createCell(0);
                    cell.setCellValue(d.text);
                    cell.setCellStyle(insertCellStyle);

                    cell = row.createCell(1);
                    cell.setCellValue("");
                    cell.setCellStyle(insertCellStyle);

                    cell = row.createCell(2);
                    cell.setCellValue(d.description);
                    cell.setCellStyle(insertCellStyle);
                    break;
                }
                case DELETE: {
                    Cell cell = row.createCell(0);
                    cell.setCellValue("");
                    cell.setCellStyle(deleteCellStyle);

                    cell = row.createCell(1);
                    cell.setCellValue(d.text);
                    cell.setCellStyle(deleteCellStyle);

                    cell = row.createCell(2);
                    cell.setCellValue(d.description);
                    cell.setCellStyle(deleteCellStyle);
                    break;
                }
            }
        }

        FileOutputStream outputStream = new FileOutputStream(outputDirectoryPath + "/" + outputFileName + ".xlsx");
        workbook.write(outputStream);
        workbook.close();
    }

}
