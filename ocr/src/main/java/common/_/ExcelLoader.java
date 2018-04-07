package common._;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;

public class ExcelLoader {

    public static String[][] loadAsArray(String fileName, int sheetIndex){
        String data[][]= {{}};

        try {
            int rows, cols;

            // Load the sheet
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(sheetIndex);
            HSSFRow row;
            HSSFCell cell;

            // Get number of rows and cols
            rows = sheet.getPhysicalNumberOfRows();
            cols = 0;
            if (rows > 0){
                cols = sheet.getRow(0).getPhysicalNumberOfCells();
            }

            // Load data to array
            data = new String[rows+1][2];
            for (int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if (row != null) {
                    for (int c = 0; c < cols; c++) {
                        cell = row.getCell((short) c);
                        if (cell != null) {
                            data[r][c] = cell.getStringCellValue();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static HashSet<String> loadAsHashSet(String fileName, int sheetIndex, int cols){
        HashSet<String> data = new HashSet<String>();

        // Load the sheet
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
            HSSFWorkbook wb = null;
            wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(sheetIndex);
            HSSFRow row;
            HSSFCell cell;

            // Load data
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i=0; i<rows; i++){
                row = sheet.getRow(i);
                if (row != null){
                    for (int j=0; j<cols; j++){
                        cell = row.getCell((short) j);
                        if (cell != null && !cell.getStringCellValue().equals("")){
                            data.add(cell.getStringCellValue().trim());
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
