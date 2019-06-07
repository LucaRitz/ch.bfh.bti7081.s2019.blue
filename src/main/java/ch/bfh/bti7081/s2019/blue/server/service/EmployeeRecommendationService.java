package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.persistence.EmployeeRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionCountPerEmployee;
import ch.bfh.bti7081.s2019.blue.server.persistence.PatientRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeRecommendationService {

    private final EmployeeRepository employeeRepository;
    private final PatientRepository patientRepository;
    private final EmployeeAvailabilityService employeeAvailabilityService;

    @Autowired
    public EmployeeRecommendationService(EmployeeRepository employeeRepository,
                                         PatientRepository patientRepository,
                                         EmployeeAvailabilityService employeeAvailabilityService) {
        this.employeeRepository = employeeRepository;
        this.patientRepository = patientRepository;
        this.employeeAvailabilityService = employeeAvailabilityService;
    }

    public void addEmployeeRecommendationsAvailableFlag(List<MissionDto> missions, DateRange planningDateRange) {

        List<MissionDto> unplannedMissions = missions.stream()
                .filter(mission -> mission.getHealthVisitor() == null)
                .collect(Collectors.toList());

        if (unplannedMissions.isEmpty()) {
            return;
        }

        Schedule schedule = getSchedule(planningDateRange);

        for (MissionDto mission : unplannedMissions) {

            DateRange dateRange = new DateRange(mission.getStartDate(), mission.getEndDate());
            List<Employee> availableEmployees = schedule.getAvailableEmployees(dateRange);
            mission.setRecommendationsAvailable(!availableEmployees.isEmpty());
        }
    }

    public List<Employee> getEmployeeRecommendations(MissionSeries missionSeries, LocalDateTime startDate, LocalDateTime endDate) {

        DateRange dateRange = new DateRange(startDate, endDate);
        Patient patient = missionSeries.getPatient();

        Schedule schedule = getSchedule(dateRange);
        PatientFamiliarityScores scores = getFamiliarityScore(patient);

        return schedule.getAvailableEmployees(dateRange)
                .stream()
                .sorted(Comparator.comparing(scores::getFamiliarity).reversed())
                .collect(Collectors.toList());
    }

    private PatientFamiliarityScores getFamiliarityScore(Patient patient) {

        PatientFamiliarityScores patientFamiliarityScores = new PatientFamiliarityScores();

        for (MissionCountPerEmployee missionCountPerEmployee :
                patientRepository.findMissionCountPerEmployeeByPatientNumber(patient.getNumber())) {

            patientFamiliarityScores.add(missionCountPerEmployee.getEmployee(), missionCountPerEmployee.getCount());
        }

        return patientFamiliarityScores;
    }

    private Schedule getSchedule(DateRange planningDateRange) {

        List<Employee> allEmployees = employeeRepository.findAll();
        EmployeeAvailabilities availabilities = employeeAvailabilityService.getAvailabilities(allEmployees, planningDateRange);

        return new Schedule(allEmployees, availabilities);
    }

}
