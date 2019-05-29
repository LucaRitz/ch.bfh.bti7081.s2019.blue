package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.service.EmployeeRecommendationService;
import ch.bfh.bti7081.s2019.blue.server.service.MissionService;
import ch.bfh.bti7081.s2019.blue.server.utils.EntityWrapper;
import ch.bfh.bti7081.s2019.blue.server.utils.MissionGenerator;
import ch.bfh.bti7081.s2019.blue.server.validator.MissionValidator;
import ch.bfh.bti7081.s2019.blue.shared.HttpUtil;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("rest/missions")
public class MissionResource {

    private final MissionRepository missionRepository;
    private final MissionSeriesRepository missionSeriesRepository;
    private final MissionGenerator generator;
    private final Mapper mapper;
    private final MissionValidator validator;
    private final EmployeeRecommendationService employeeRecommendationService;
    private final MissionService missionService;

    @Autowired
    public MissionResource(MissionRepository missionRepository,
                           MissionSeriesRepository missionSeriesRepository,
                           MissionGenerator generator,
                           Mapper mapper,
                           MissionValidator validator,
                           EmployeeRecommendationService employeeRecommendationService,
                           MissionService missionService) {
        this.missionRepository = missionRepository;
        this.missionSeriesRepository = missionSeriesRepository;
        this.generator = generator;
        this.mapper = mapper;
        this.validator = validator;
        this.employeeRecommendationService = employeeRecommendationService;
        this.missionService = missionService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public @ResponseBody
    List<MissionDto> find(@RequestParam Integer patientNumber,
                          @RequestParam @DateTimeFormat(pattern = HttpUtil.DATE_TIME_FORMAT) Date startDate,
                          @RequestParam @DateTimeFormat(pattern = HttpUtil.DATE_TIME_FORMAT) Date endDate) {
        List<Mission> missions = new ArrayList<>(missionRepository.findByPatientNumberAndIntersectingDateRange(patientNumber, startDate, endDate));
        List<MissionSeries> series = new ArrayList<>(missionSeriesRepository.findByPatientNumberAndIntersectingDateRange(patientNumber, startDate, endDate));
        List<Mission> temporaryMissions = generator.generateMissionsFromSeries(series, new DateRange(startDate, endDate));

        List<Mission> mergedMissions = missionService.mergeExistingMissionsWithTemporaryOnes(missions, temporaryMissions);

        List<MissionDto> result = mapper.map(mergedMissions, MissionDto.class);
        employeeRecommendationService.addEmployeeRecommendationsAvailableFlag(result, new DateRange(startDate, endDate));
        return result;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public void create(@RequestBody MissionDto dto) {
        Mission entity = mapper.map(dto, Mission.class);
        validator.validate(new EntityWrapper<>(null, entity));
        missionRepository.save(entity);
    }

}
