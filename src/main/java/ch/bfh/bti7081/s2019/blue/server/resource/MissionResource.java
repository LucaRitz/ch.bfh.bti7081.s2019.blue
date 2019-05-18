package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.utils.DateRange;
import ch.bfh.bti7081.s2019.blue.server.utils.MissionGenerator;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("rest/missions")
public class MissionResource implements MissionService {

    private final MissionRepository missionRepository;
    private final MissionSeriesRepository missionSeriesRepository;
    private final MissionGenerator generator;
    private final Mapper mapper;

    @Autowired
    public MissionResource(MissionRepository missionRepository, MissionSeriesRepository missionSeriesRepository, MissionGenerator generator, Mapper mapper) {
        this.missionRepository = missionRepository;
        this.missionSeriesRepository = missionSeriesRepository;
        this.generator = generator;
        this.mapper = mapper;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public List<MissionDto> find(@RequestParam Integer patientNumber,
                                 @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                 @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<Mission> missions = new ArrayList<>(missionRepository.findByPatientNumberAndIntersectingDateRange(patientNumber, startDate, endDate));
        List<MissionSeries> series = new ArrayList<>(missionSeriesRepository.findByPatientNumberAndIntersectingDateRange(patientNumber, startDate, endDate));
        List<Mission> temporaryMissions = generator.generateMissionsFromSeries(series, new DateRange(startDate, endDate));

        List<Mission> mergedMissions = mergeExistingMissionsWithTemporaryOnes(missions, temporaryMissions);

        return mapper.map(mergedMissions, MissionDto.class);
    }

    private List<Mission> mergeExistingMissionsWithTemporaryOnes(List<Mission> missions, List<Mission> temporaryMissions) {
        List<Mission> mergedMissions = new ArrayList<>(missions);


        for (Mission temporaryMission : temporaryMissions) {
            boolean isAlreadyAnExistingMission =
                    missions
                    .stream()
                        .anyMatch(existingMission ->
                                temporaryMission.getStartDate().equals(existingMission.getStartDate()) &&
                                temporaryMission.getEndDate().equals(existingMission.getEndDate()) &&
                                temporaryMission.getMissionSeries().getId().equals(existingMission.getMissionSeries().getId())
                        );

            if (!isAlreadyAnExistingMission) {
                mergedMissions.add(temporaryMission);
            }
        }

        return mergedMissions;
    }
}
