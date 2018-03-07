package common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelLoader {

    private String fileName;

    private String RuleArray[][];

    private int sheetIndex;

    public ExcelLoader(String fileName){
        this.fileName = fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public void setSheetIndex(int sheetIndex){
        this.sheetIndex = sheetIndex;
    }

    public String[][] loadData(){
        int rows, cols;

        try {
            // Load the sheet
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.fileName));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(this.sheetIndex);
            HSSFRow row;
            HSSFCell cell;

            // Get number of rows and cols
            rows = sheet.getPhysicalNumberOfRows();
            cols = 0;
            if (rows > 0){
                cols = sheet.getRow(0).getPhysicalNumberOfCells();
            }

            // Load data to array
            RuleArray = new String[rows+1][2];
            for (int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if (row != null) {
                    for (int c = 0; c < cols; c++) {
                        cell = row.getCell((short) c);
                        if (cell != null) {
                            RuleArray[r][c] = cell.getStringCellValue();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return RuleArray;
    }
}
