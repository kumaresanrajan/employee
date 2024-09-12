package com.swissre.employee.controller;

import com.swissre.employee.model.EmployeeDetails;
import com.swissre.employee.service.EmployeeManagementService;
import com.swissre.employee.util.CSVFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/employee")
public class EmployeeManagementController {

    Logger logger = LoggerFactory.getLogger(EmployeeManagementController.class);
    @Autowired
    CSVFileReader fileReader;
    @Autowired
    EmployeeManagementService employeeManagementService;

    /**
     * This API accepts csv file to get the employee's information as like
     * 1. which managers earn less than they should, and by how much
     * 2. which managers earn more than they should, and by how much
     * 3. which employees have a reporting line which is too long, and by how much
     * @param inputFile
     * @return
     */
    @PostMapping(produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Set<EmployeeDetails>>> getEmployeeDetails(@RequestParam(value ="file") final MultipartFile inputFile) {
        Map<String, Set<EmployeeDetails>> employeeDetailsMap;
        try {
            if(StringUtils.getFilenameExtension(inputFile.getOriginalFilename()).toLowerCase().equals("csv")){
                employeeDetailsMap = employeeManagementService
                        .getEmployeeResponseData(fileReader.parseCSVFile(inputFile.getInputStream()));

                System.out.println(employeeDetailsMap);
                logger.info("Output Data => {}", employeeDetailsMap);
            }else{
                return ResponseEntity.badRequest().build();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  ResponseEntity.ok(employeeDetailsMap);
    }
}
