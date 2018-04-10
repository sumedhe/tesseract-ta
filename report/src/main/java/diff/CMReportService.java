package diff;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CMReportService {

    /**
     * @param confusionMap
     */
    public static void generateDefault(TreeMap<String, TreeMap<String, Integer>> confusionMap, String outputDirectoryPath, String outputFileName) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet();
        for (int i = 0; i < confusionMap.size(); i++){
            sheet.setColumnWidth(i, 2000);
        }

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle oddCellStyle = workbook.createCellStyle();
        oddCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        oddCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle evenCellStyle = workbook.createCellStyle();
        evenCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        evenCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HashMap<String, Integer> positionMap = new HashMap<>();

        int i = 1;
        Row headerRow = sheet.createRow(0);
        for(Map.Entry<String, TreeMap<String, Integer>> entry: confusionMap.entrySet()){
            String letter = entry.getKey()
                    .replace("\n", "<n>")
                    .replace("\t", "<t>")
                    .replace("\r", "<r>")
                    .replace("\b", "<b>")
                    .replace(" ", "<s>");

            positionMap.put(entry.getKey(), i);

            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(letter);

            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(letter);

            if(i % 2 == 1){
                headerCell.setCellStyle(oddCellStyle);
                cell.setCellStyle(oddCellStyle);
            }else{
                headerCell.setCellStyle(evenCellStyle);
                cell.setCellStyle(evenCellStyle);
            }

            i++;
        }

        for(Map.Entry<String, TreeMap<String, Integer>> entry: confusionMap.entrySet()){
            int currentPos = positionMap.get(entry.getKey());
            Row row = sheet.getRow(currentPos);

            for(Map.Entry<String, Integer> subEntry: entry.getValue().entrySet()){
                Cell cell = row.createCell(currentPos);
                cell.setCellValue(subEntry.getValue());
            }
        }


        FileOutputStream outputStream = new FileOutputStream(outputDirectoryPath + "/" + outputFileName + ".xlsx");
        workbook.write(outputStream);
        workbook.close();
    }

}
