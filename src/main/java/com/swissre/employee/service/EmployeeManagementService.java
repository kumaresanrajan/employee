package com.swissre.employee.service;

import com.swissre.employee.model.Employee;
import com.swissre.employee.model.EmployeeDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EmployeeManagementService {
    Map<String, Set<EmployeeDetails>> getEmployeeResponseData(List<Map<String, String>> employeeUnstructredData);
}
