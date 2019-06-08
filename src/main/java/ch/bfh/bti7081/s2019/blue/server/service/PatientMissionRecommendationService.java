package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;
import ch.bfh.bti7081.s2019.blue.server.utils.MissionGenerator;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientMissionRecommendationService {

    private final MissionSeriesRepository missionSeriesRepository;
    private final EmployeeAvailabilityService employeeAvailabilityService;
    private final MissionGenerator missionGenerator;
    private final FamiliarityService familiarityService;
    private final MissionService missionService;
    private final MissionRepository missionRepository;
    private final Mapper mapper;

    @Autowired
    public PatientMissionRecommendationService(MissionSeriesRepository missionSeriesRepository,
                                               EmployeeAvailabilityService employeeAvailabilityService,
                                               MissionGenerator missionGenerator,
                                               FamiliarityService familiarityService,
                                               MissionService missionService,
                                               MissionRepository missionRepository, Mapper mapper) {
        this.missionSeriesRepository = missionSeriesRepository;
        this.employeeAvailabilityService = employeeAvailabilityService;
        this.missionGenerator = missionGenerator;
        this.familiarityService = familiarityService;
        this.missionService = missionService;
        this.missionRepository = missionRepository;
        this.mapper = mapper;
    }

    public List<MissionDto> getPatientMissionRecommendations(Employee employee, DateRange dateRange) {

        EmployeeAvailability availability = employeeAvailabilityService.getAvailability(employee, dateRange);
        List<Mission> possibleMissions = getPossibleMissions(availability, dateRange);

        EmployeeFamiliarityScores employeeFamiliarityScores = familiarityService.getFamiliarityScores(employee);

        return mapper.map(possibleMissions.stream()
                .sorted(getComparator(employeeFamiliarityScores))
                .collect(Collectors.toList()), MissionDto.class);
    }

    private Comparator<Mission> getComparator(EmployeeFamiliarityScores employeeFamiliarityScores) {
        Comparator<Mission> comparator = Comparator.comparing(mission -> {
            Patient patient = mission.getMissionSeries().getPatient();
            return employeeFamiliarityScores.getFamiliarity(patient);
        });
        return comparator.reversed();
    }

    public List<DateRange> getPatientMissionRecommendationPlaceholders(Employee employee, DateRange planningDateRange) {

        EmployeeAvailability availability = employeeAvailabilityService.getAvailability(employee, planningDateRange);
        List<DateRange> possibleMissions = getPossibleMissions(availability, planningDateRange)
                .stream()
                .map(this::getDateRange)
                .collect(Collectors.toList());

        return mergeOverlappingDateRanges(possibleMissions);
    }

    private List<DateRange> mergeOverlappingDateRanges(List<DateRange> intervals) {
        if (intervals.size() == 0 || intervals.size() == 1) {
            return intervals;
        }
        intervals.sort(Comparator.comparing(DateRange::getStartDate));

        DateRange first = intervals.get(0);
        LocalDateTime start = first.getStartDate();
        LocalDateTime end = first.getEndDate();

        List<DateRange> result = new ArrayList<>();

        for (int i = 1; i < intervals.size(); i++) {
            DateRange current = intervals.get(i);
            if (current.getStartDate().isBefore(end) || current.getStartDate().equals(end)) {
                end = current.getEndDate().isAfter(end) ? current.getEndDate() : end;
            } else {
                result.add(new DateRange(start, end));
                start = current.getStartDate();
                end = current.getEndDate();
            }
        }

        result.add(new DateRange(start, end));
        return result;
    }

    private List<Mission> getPossibleMissions(EmployeeAvailability availability, DateRange planningDateRange) {

        List<MissionSeries> missionSeries = missionSeriesRepository
                .findByIntersectingDateRange(planningDateRange.getStartDate(), planningDateRange.getEndDate());

        List<Mission> temporaryMissions = missionGenerator.generateMissionsFromSeries(missionSeries, planningDateRange);
        List<Mission> missions = missionRepository
                .findByIntersectingDateRange(planningDateRange.getStartDate(), planningDateRange.getEndDate(), null);
        List<Mission> mergedMissions = missionService.mergeExistingMissionsWithTemporaryOnes(missions, temporaryMissions);
        mergedMissions.removeAll(missions);

        return mergedMissions.stream()
                .filter(mission -> availability.isAvailable(getDateRange(mission)))
                .collect(Collectors.toList());
    }

    private DateRange getDateRange(Mission mission) {
        return new DateRange(mission.getStartDate(), mission.getEndDate());
    }

}
