package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;

import java.util.HashMap;
import java.util.Map;

public class EmployeeFamiliarityScores {

    private Map<Integer, Long> missionCountPerPatient;

    public EmployeeFamiliarityScores() {
        missionCountPerPatient = new HashMap<>();
    }

    public void add(Patient patient, Long missionCount) {
        missionCountPerPatient.put(patient.getId(), missionCount);
    }

    public Long getFamiliarity(Patient patient) {
        return missionCountPerPatient.getOrDefault(patient.getId(), 0L);
    }
}
