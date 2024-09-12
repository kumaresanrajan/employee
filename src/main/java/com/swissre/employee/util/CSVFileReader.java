package com.swissre.employee.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class CSVFileReader {
    /**
     * Parsing the csv file data into map row by row
     * @param fileInputStream
     * @return
     */
    public List<Map<String, String>> parseCSVFile(final InputStream fileInputStream) {
        List<Map<String, String>> csvFileData = new ArrayList<>();
        Set<String> columnName = new LinkedHashSet<>();
        Map<String, String> rowData;
        try(CSVReader reader = new CSVReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8))){
           String row [] = null;
           int rowCount =0;
            while((row = reader.readNext())!=null){
                if(rowCount ==0){
                    columnName.addAll(Arrays.asList(row));
                    rowCount++;
                }else{
                    int cellNumber = 0;
                    rowData = new HashMap<>();
                    for(String column:columnName){
                        rowData.put(column, row[cellNumber]);
                        cellNumber++;
                    }
                    csvFileData.add(rowData);
                }

            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return csvFileData;
    }
}
