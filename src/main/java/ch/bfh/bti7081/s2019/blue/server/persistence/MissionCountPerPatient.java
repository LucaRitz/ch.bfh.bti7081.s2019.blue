package ch.bfh.bti7081.s2019.blue.server.persistence;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;

public class MissionCountPerPatient {

    private Long count;
    private Patient patient;

    public MissionCountPerPatient(Long count, Patient patient) {
        this.count = count;
        this.patient = patient;
    }

    public Long getCount() {
        return count;
    }

    public Patient getPatient() {
        return patient;
    }
}
