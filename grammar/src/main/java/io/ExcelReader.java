package io;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ExcelReader {

    private static int sheetIndex = 0;

    public static String[][] readAsMatrix(String fileName) {
        String data[][] = {{}};

        // Load the sheet
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(sheetIndex);
            HSSFRow row;
            HSSFCell cell;

            // Get number of rows and cols
            int rows, cols;
            rows = sheet.getPhysicalNumberOfRows();
            cols = 0;
            if (rows > 0) {
                cols = sheet.getRow(0).getPhysicalNumberOfCells();
            }

            // Load data to array
            data = new String[rows + 1][2];
            for (int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if (row != null) {
                    for (int c = 0; c < cols; c++) {
                        cell = row.getCell((short) c);
                        if (cell != null) {
                            data[r][c] = cell.getStringCellValue().trim();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }


}
