package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.utils.MissionGenerator;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PatientMissionRecommendationService {

    private final MissionSeriesRepository missionSeriesRepository;
    private final EmployeeAvailabilityService employeeAvailabilityService;
    private final MissionGenerator missionGenerator;
    private final FamiliarityService familiarityService;
    private final MissionService missionService;
    private final MissionRepository missionRepository;

    @Autowired
    public PatientMissionRecommendationService(MissionSeriesRepository missionSeriesRepository,
                                               EmployeeAvailabilityService employeeAvailabilityService,
                                               MissionGenerator missionGenerator,
                                               FamiliarityService familiarityService,
                                               MissionService missionService,
                                               MissionRepository missionRepository) {
        this.missionSeriesRepository = missionSeriesRepository;
        this.employeeAvailabilityService = employeeAvailabilityService;
        this.missionGenerator = missionGenerator;
        this.familiarityService = familiarityService;
        this.missionService = missionService;
        this.missionRepository = missionRepository;
    }

    public List<Mission> getPatientMissionRecommendations(Employee employee, DateRange dateRange) {

        EmployeeAvailability availability = employeeAvailabilityService.getAvailability(employee, dateRange);
        List<Mission> possibleMissions = getPossibleMissions(employee, availability, dateRange);

        EmployeeFamiliarityScores employeeFamiliarityScores = familiarityService.getFamiliarityScores(employee);

        return possibleMissions.stream()
                .sorted(Comparator.comparing(mission -> employeeFamiliarityScores.getFamiliarity(mission.getMissionSeries().getPatient())))
                .collect(Collectors.toList());
    }

    public List<DateRange> getPatientMissionRecommendationPlaceholders(Employee employee, DateRange planningDateRange) {

        EmployeeAvailability availability = employeeAvailabilityService.getAvailability(employee, planningDateRange);
        List<DateRange> possibleMissions = getPossibleMissions(employee, availability, planningDateRange)
                .stream()
                .map(this::getDateRange)
                .collect(Collectors.toList());

        List<DateRange> dateRanges = new ArrayList<>();
        List<DateRange> ignored = new ArrayList<>();
        for (DateRange possibleMission : possibleMissions) {

            if (ignored.contains(possibleMission)) {
                continue;
            }

            List<DateRange> conflictingMissions = getConflicts(possibleMission, possibleMissions);

            DateRange dateRange = getSpanningDateRange(conflictingMissions)
                    .orElseGet(() -> new DateRange(possibleMission.getStartDate(), possibleMission.getEndDate()));

            dateRanges.add(dateRange);
            ignored.addAll(conflictingMissions);
        }

        return dateRanges;
    }

    private Optional<DateRange> getSpanningDateRange(List<DateRange> dateRanges) {

        if (dateRanges.isEmpty()) {
            return Optional.empty();
        }

        Date startDate = dateRanges.stream()
                .map(DateRange::getStartDate)
                .min(Comparator.naturalOrder())
                .orElse(null);

        Date endDate = dateRanges.stream()
                .map(DateRange::getEndDate)
                .max(Comparator.naturalOrder())
                .orElse(null);

        return Optional.of(new DateRange(startDate, endDate));
    }

    private List<DateRange> getConflicts(DateRange range, List<DateRange> ranges) {

        return ranges.stream()
                .filter(m -> m.intersects(range))
                .collect(Collectors.toList());
    }

    private List<Mission> getPossibleMissions(Employee employee, EmployeeAvailability availability, DateRange planningDateRange) {

        List<MissionSeries> missionSeries = missionSeriesRepository
                .findByIntersectingDateRange(planningDateRange.getStartDate(), planningDateRange.getEndDate());

        List<Mission> temporaryMissions = missionGenerator.generateMissionsFromSeries(missionSeries, planningDateRange);
        List<Mission> missions = missionRepository
                .findByHealthVisitorAndIntersectingDateRange(
                        employee.getId(), planningDateRange.getStartDate(), planningDateRange.getEndDate());
        missionService.mergeExistingMissionsWithTemporaryOnes(missions, temporaryMissions);

        return temporaryMissions.stream()
                .filter(mission -> availability.isAvailable(getDateRange(mission)))
                .collect(Collectors.toList());
    }

    private DateRange getDateRange(Mission mission) {
        return new DateRange(mission.getStartDate(), mission.getEndDate());
    }

}
