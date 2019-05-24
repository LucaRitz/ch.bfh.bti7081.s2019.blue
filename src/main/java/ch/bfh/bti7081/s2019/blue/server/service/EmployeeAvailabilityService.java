package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.persistence.EmployeeRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmployeeAvailabilityService {

    private final EmployeeRepository employeeRepository;
    private final MissionRepository missionRepository;

    @Autowired
    public EmployeeAvailabilityService(EmployeeRepository employeeRepository,
                                       MissionRepository missionRepository) {
        this.employeeRepository = employeeRepository;
        this.missionRepository = missionRepository;
    }

    public EmployeeAvailability getAvailability(Employee employee, DateRange planningDateRange) {

        List<DateRange> occupations = new ArrayList<>();

        occupations.addAll(
                missionRepository.findByHealthVisitorAndIntersectingDateRange(employee.getId(),
                        planningDateRange.getStartDate(), planningDateRange.getEndDate())
                        .stream()
                        .map(mission -> new DateRange(mission.getStartDate(), mission.getEndDate()))
                        .collect(Collectors.toList())
        );

        occupations.addAll(
                employeeRepository.findAbsencesByHealthVisitorAndIntersectingDateRange(employee.getId(),
                        planningDateRange.getStartDate(), planningDateRange.getEndDate())
                        .stream()
                        .map(mission -> new DateRange(mission.getStartDate(), mission.getEndDate()))
                        .collect(Collectors.toList())
        );

        return new EmployeeAvailability(occupations);
    }

    public EmployeeAvailabilities getAvailabilities(List<Employee> employees, DateRange planningDateRange) {
        return new EmployeeAvailabilities(getAvailabilityPerEmployee(employees, planningDateRange));
    }

    private Map<Integer, EmployeeAvailability> getAvailabilityPerEmployee(List<Employee> employees, DateRange planningDateRange) {
        return employees.stream()
                .collect(Collectors.toMap(
                        Employee::getId,
                        employee -> getAvailability(employee, planningDateRange)));
    }

}
