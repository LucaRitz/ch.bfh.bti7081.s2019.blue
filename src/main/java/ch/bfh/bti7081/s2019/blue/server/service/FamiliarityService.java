package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.persistence.MissionCountPerEmployee;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionCountPerPatient;
import ch.bfh.bti7081.s2019.blue.server.persistence.PatientRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FamiliarityService {

    private final PatientRepository patientRepository;

    @Autowired
    public FamiliarityService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientFamiliarityScores getFamiliarityScores(PatientRefDto patient) {

        PatientFamiliarityScores scores = new PatientFamiliarityScores();

        for (MissionCountPerEmployee missionCountPerEmployee :
                patientRepository.findMissionCountPerEmployeeByPatientNumber(patient.getNumber())) {

            scores.add(missionCountPerEmployee.getEmployee(), missionCountPerEmployee.getCount());
        }

        return scores;
    }

    public EmployeeFamiliarityScores getFamiliarityScores(Employee employee) {

        EmployeeFamiliarityScores scores = new EmployeeFamiliarityScores();

        for (MissionCountPerPatient missionCountPerPatient :
                patientRepository.findMissionCountPerPatientByEmployee(employee.getId())) {

            scores.add(missionCountPerPatient.getPatient(), missionCountPerPatient.getCount());
        }

        return scores;
    }

}
