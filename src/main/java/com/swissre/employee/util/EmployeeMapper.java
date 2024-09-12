package com.swissre.employee.util;

import com.swissre.employee.constants.EmployeeConstants;
import com.swissre.employee.model.Employee;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class EmployeeMapper {

    public Set<Employee> mapToEmployee(final List<Map<String, String>> inputSource){
        Set<Employee> employees = new HashSet<>();
        Employee employee;
        for(Map<String, String> inputMap:inputSource){
            employee = new Employee();
            employee.setId(Long.parseLong(inputMap.get(EmployeeConstants.EMP_ID)));
            employee.setFirstName(inputMap.get(EmployeeConstants.FIRST_NAME));
            employee.setLastName(inputMap.get(EmployeeConstants.LAST_NAME));
            if(ObjectUtils.isNotEmpty(inputMap.get(EmployeeConstants.MANAGER_ID))){
                employee.setManagerId(Long.parseLong(inputMap.get(EmployeeConstants.MANAGER_ID)));
            }
            employee.setSalary(Double.parseDouble(inputMap.get(EmployeeConstants.SALARY)));
            employees.add(employee);
        }
        return employees;
    }
}
