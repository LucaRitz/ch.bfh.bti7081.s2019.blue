package ch.bfh.bti7081.s2019.blue.server.persistence;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT new ch.bfh.bti7081.s2019.blue.server.persistence.MissionCountPerEmployee(COUNT(m), e)"
            + " FROM Mission m"
            + " JOIN MissionSeries ms ON m.missionSeries = ms"
            + " JOIN Employee e ON m.healthVisitor = e"
            + " JOIN Patient p ON ms.patient = p"
            + " WHERE p.number = :patientNr"
            + " GROUP BY e.id")
    List<MissionCountPerEmployee> findMissionCountPerEmployeeByPatientNumber(@Param("patientNr") Integer patientNr);

    @Query("SELECT new ch.bfh.bti7081.s2019.blue.server.persistence.MissionCountPerPatient(COUNT(m), p)"
            + " FROM Mission m"
            + " JOIN MissionSeries ms ON m.missionSeries = ms"
            + " JOIN Employee e ON m.healthVisitor = e"
            + " JOIN Patient p ON ms.patient = p"
            + " WHERE e.id = :employeeId"
            + " GROUP BY p.id")
    List<MissionCountPerPatient> findMissionCountPerPatientByEmployee(@Param("employeeId") Integer employeeId);

}
