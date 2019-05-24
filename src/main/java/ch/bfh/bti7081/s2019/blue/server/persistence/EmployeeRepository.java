package ch.bfh.bti7081.s2019.blue.server.persistence;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Absence;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT a FROM Absence a"
            + " JOIN Employee e ON a.employee = e"
            + " WHERE e.id = :healthVisitorId"
            + " AND a.startDate <= :endDate AND a.endDate >= :startDate")
    List<Absence> findAbsencesByHealthVisitorAndIntersectingDateRange(
            @Param("healthVisitorId") Integer healthVisitorId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}
