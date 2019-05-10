package ch.bfh.bti7081.s2019.blue.server.persistence;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MissionSeriesRepository extends JpaRepository<MissionSeries, Long> {

    @Query("SELECT ms FROM MissionSeries ms"
            + " JOIN Patient p ON ms.patient = p"
            + " WHERE p.number = :patientNumber"
            + " and ms.startDate <= :endDate and ms.endDate >= :startDate")
    List<MissionSeries> findByPatientNumberAndIntersectingDateRange(
            @Param("patientNumber") Integer patientNumber,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}
