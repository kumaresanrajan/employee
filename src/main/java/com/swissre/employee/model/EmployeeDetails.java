package com.swissre.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EmployeeDetails {
    long employeeId;
    String firstName;
    String lastName;
    @JsonIgnore
    double salary;
    @JsonIgnore
    Set<Employee> reportees = new HashSet<>();
    @JsonIgnore
    Set<Employee> managerHierarchy = new HashSet<>();
    @JsonIgnore
    long managerId;
    int hierarchyLevel;
    double salaryDifference;

    @Override
    public String toString() {
        return "EmployeeDetails{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", reportees=" + reportees +
                ", managerHierarchy=" + managerHierarchy +
                ", managerId=" + managerId +
                ", hierarchyLevel=" + hierarchyLevel +
                ", salaryPercent=" + salaryDifference +
                '}';
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setReportees(Set<Employee> reportees) {
        this.reportees = reportees;
    }

    public void setManagerHierarchy(Set<Employee> managerHierarchy) {
        this.managerHierarchy = managerHierarchy;
    }

    public void setHierarchyLevel(int hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    public void setSalaryPercent(double salaryPercent) {
        this.salaryDifference = salaryPercent;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getSalary() {
        return salary;
    }

    public Set<Employee> getReportees() {
        return reportees;
    }

    public Set<Employee> getManagerHierarchy() {
        return managerHierarchy;
    }

    public int getHierarchyLevel() {
        return hierarchyLevel;
    }

    public double getSalaryPercent() {
        return salaryDifference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDetails that = (EmployeeDetails) o;
        return employeeId == that.employeeId && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName);
    }

}
