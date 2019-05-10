package ch.bfh.bti7081.s2019.blue.server.persistence;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("SELECT m FROM Mission m"
            + " JOIN Employee h ON m.healthVisitor = h"
            + " WHERE h.id = :healthVisitorId"
            + " AND m.startDate <= :endDate AND m.endDate >= :startDate")
    List<Mission> findByHealthVisitorAndIntersectingDateRange(
            @Param("healthVisitorId") Integer healthVisitorId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}
