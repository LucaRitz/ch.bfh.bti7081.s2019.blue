package ch.bfh.bti7081.s2019.blue.server.persistence;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MissionSeriesRepository extends JpaRepository<MissionSeries, Integer> {

    @Query("SELECT ms FROM MissionSeries ms"
            + " JOIN Patient p ON ms.patient = p"
            + " WHERE p.number = :patientNumber"
            + " and ms.startDate <= :endDate and ms.endDate >= :startDate")
    List<MissionSeries> findByPatientNumberAndIntersectingDateRange(
            @Param("patientNumber") Integer patientNumber,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT ms FROM MissionSeries ms"
            + " JOIN Patient p ON ms.patient = p"
            + " WHERE ms.startDate <= :endDate and ms.endDate >= :startDate")
    List<MissionSeries> findByIntersectingDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}
