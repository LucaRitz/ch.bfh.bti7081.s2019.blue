package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;

import java.util.ArrayList;
import java.util.Map;

public class EmployeeAvailabilities {

    private final Map<Integer, EmployeeAvailability> availabilityPerEmployee;

    public EmployeeAvailabilities(Map<Integer, EmployeeAvailability> availabilityPerEmployee) {
        this.availabilityPerEmployee = availabilityPerEmployee;
    }

    public boolean isAvailable(Employee employee, DateRange dateRange) {
        return availabilityPerEmployee
                .getOrDefault(employee.getId(), new EmployeeAvailability(new ArrayList<>()))
                .isAvailable(dateRange);
    }
}
