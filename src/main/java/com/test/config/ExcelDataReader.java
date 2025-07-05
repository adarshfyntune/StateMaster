package com.test.config;



import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Component
@StepScope
public class ExcelDataReader implements ItemReader<PinCodeSearchDto> {
    private final Iterator<Row> rowIterator;
    private Long pinCode;


    public ExcelDataReader(@Value("#{jobParameters['filePath']}") String filepath) throws IOException {
        InputStream inputStream = new FileInputStream(filepath);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        this.rowIterator = sheet.iterator();
        this.rowIterator.next();

    }


//    @Override
//    public PinCodeSearchDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        if (!rowIterator.hasNext()) return null;
//
//        Row row = rowIterator.next();
//
//        Long pincode=(long) row.getCell(2).getNumericCellValue();
//        String cityName=row.getCell(1).getStringCellValue();
//        String stateName=row.getCell(0).getStringCellValue();
//        return new PinCodeSearchDto(pincode,cityName,stateName);
//    }

    @Override
    public PinCodeSearchDto read() throws Exception {
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int rowNum = row.getRowNum();

            try {
                Cell stateCell = row.getCell(0);
                Cell cityCell = row.getCell(1);
                Cell pincodeCell = row.getCell(2);

                if (stateCell == null || cityCell == null || pincodeCell == null) {
                    System.err.println("⚠️ Skipping row " + rowNum + ": Missing required cell");
                    continue;
                }

                String stateName = stateCell.getStringCellValue().trim();
                String cityName = cityCell.getStringCellValue().trim();
                Long pincode = (long) pincodeCell.getNumericCellValue();

                if (stateName.isEmpty() || cityName.isEmpty()) {
                    System.err.println("⚠️ Skipping row " + rowNum + ": Empty state or city");
                    continue;
                }



                return new PinCodeSearchDto(stateName,cityName,pincode);

            } catch (Exception ex) {
                System.err.println("Error reading row " + rowNum + ": " + ex.getMessage());
                // Optionally log stack trace
                continue;
            }
        }

        return null; // no more rows
    }
}
