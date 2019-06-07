package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;

import java.util.HashMap;
import java.util.Map;

public class PatientFamiliarityScores {

    private final Map<Integer, Long> missionCountPerEmployee;

    public PatientFamiliarityScores() {
        missionCountPerEmployee = new HashMap<>();
    }

    public void add(Employee employee, Long missionCount) {
        missionCountPerEmployee.put(employee.getId(), missionCount);
    }

    public Long getFamiliarity(Employee employee) {
        return missionCountPerEmployee.getOrDefault(employee.getId(), 0L);
    }
}
