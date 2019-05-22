package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;

import java.util.List;
import java.util.stream.Collectors;

public class Schedule {

    private List<Employee> employees;
    private EmployeeAvailabilities employeeAvailabilities;

    public Schedule(List<Employee> employees, EmployeeAvailabilities employeeAvailabilities) {
        this.employees = employees;
        this.employeeAvailabilities = employeeAvailabilities;
    }

    public List<Employee> getAvailableEmployees(DateRange dateRange) {
        return employees.stream()
                .filter(e -> isAvailable(e, dateRange))
                .collect(Collectors.toList());
    }

    private boolean isAvailable(Employee employee, DateRange dateRange) {
        return employeeAvailabilities.isAvailable(employee, dateRange);
    }
}
