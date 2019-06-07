package ch.bfh.bti7081.s2019.blue.server.persistence;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query("SELECT r FROM Report r"
            + " JOIN Mission m ON r.mission = m"
            + " WHERE m.id = :missionId")
    Report getByMissionId(Integer missionId);
}
