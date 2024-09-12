package com.swissre.employee.service.impl;

import com.swissre.employee.constants.EmployeeConstants;
import com.swissre.employee.model.Employee;
import com.swissre.employee.model.EmployeeDetails;
import com.swissre.employee.service.EmployeeManagementService;
import com.swissre.employee.util.EmployeeMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * This method used to populate the structured Employee object
     * from csv parsed list of map data
     * @param employeeUnstructredData
     * @return
     */
    private Set<Employee> getEmployee(final List<Map<String, String>> employeeUnstructredData) {
        return employeeMapper.mapToEmployee(employeeUnstructredData);
    }

    /**
     * Populate the EmployeeDetails object from set of Employee object
     * @param employees
     * @return
     */
    private Set<EmployeeDetails> populateEmployeeDetails(Set<Employee> employees) {
        Set<EmployeeDetails> employeeDetailsList = new HashSet<>();
        for(Employee employee:employees){
            EmployeeDetails details = new EmployeeDetails();
            details.setEmployeeId(employee.getId());
            details.setFirstName(employee.getFirstName());
            details.setLastName(employee.getLastName());
            details.setSalary(employee.getSalary());
            details.setManagerId(employee.getManagerId());
            details.setReportees(employees.stream()
                    .filter(employee1 -> employee.getId() == employee1.getManagerId())
                    .collect(Collectors.toSet()));
            updateHierarchy(employee.getManagerId(), employees, details);
            details.setHierarchyLevel(details.getManagerHierarchy().size());
            if(CollectionUtils.isNotEmpty(details.getReportees())){
                calculateSalaryPercentage(details, details.getReportees());
            }
            employeeDetailsList.add(details);
        }
        return employeeDetailsList;
    }

    /**
     * Get Employee response data from the parsed data
     * @param employeeUnstructredData
     * @return
     */
    @Override
    public Map<String, Set<EmployeeDetails>> getEmployeeResponseData(final List<Map<String, String>> employeeUnstructredData) {
        Set<EmployeeDetails> employeeDetails = populateEmployeeDetails(getEmployee(employeeUnstructredData));
        Map<String, Set<EmployeeDetails>> employeeDetailsMap = new HashMap<>();
        employeeDetails.forEach(employeeDetails1 -> {
            if(employeeDetails1.getSalaryPercent()<20
                    && CollectionUtils.isNotEmpty(employeeDetails1.getReportees())){
                employeeDetailsMap.computeIfAbsent(EmployeeConstants.LESS_SALARY,
                        val-> new HashSet<>()).add(employeeDetails1);
            }else if(employeeDetails1.getSalaryPercent()>50
                    && CollectionUtils.isNotEmpty(employeeDetails1.getReportees())){
                employeeDetailsMap.computeIfAbsent(EmployeeConstants.MORE_SALARY,
                        val-> new HashSet<>()).add(employeeDetails1);
            }
            if(employeeDetails1.getManagerHierarchy().size()>4){
                employeeDetailsMap.computeIfAbsent(EmployeeConstants.LONG_HIERARCHY,
                        val-> new HashSet<>()).add(employeeDetails1);
            }
        });
        return employeeDetailsMap;
    }

    /**
     * Recursion method to get all the managers in the hierarchy
     * @param managerId
     * @param employees
     * @param employeeDetails
     */
    private void updateHierarchy(final long managerId,
                                 final Set<Employee> employees, EmployeeDetails employeeDetails){
        for(Employee emp: employees){
            if(managerId == emp.getId()){
                employeeDetails.getManagerHierarchy().add(emp);
                updateHierarchy(emp.getManagerId(), employees, employeeDetails);
            }
        }
    }

    /**
     * This method used to calculate the salary difference percentage
     * from average salary of reportees under the manager
     * formula used [ (x1-x2)/((x1+x2)/2)]*100
     * @param employeeDetails
     * @param employees
     */
    private void calculateSalaryPercentage(EmployeeDetails employeeDetails,
                                          final Set<Employee> employees){
        OptionalDouble salaryAverage=employees.stream().mapToDouble(Employee::getSalary).average();
        if(salaryAverage.isPresent()){
            employeeDetails.setSalaryPercent(
                    ((employeeDetails.getSalary()-salaryAverage.getAsDouble())
                    /((employeeDetails.getSalary()+salaryAverage.getAsDouble())/2)*100));
        }
    }
}
