package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.ReportRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Report;
import ch.bfh.bti7081.s2019.blue.server.service.EmployeeRecommendationService;
import ch.bfh.bti7081.s2019.blue.server.service.MissionService;
import ch.bfh.bti7081.s2019.blue.server.utils.EntityWrapper;
import ch.bfh.bti7081.s2019.blue.server.utils.MissionGenerator;
import ch.bfh.bti7081.s2019.blue.server.validator.MissionValidator;
import ch.bfh.bti7081.s2019.blue.server.validator.ValidationException;
import ch.bfh.bti7081.s2019.blue.shared.HttpUtil;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("rest/missions")
public class MissionResource {

    private final MissionRepository missionRepository;
    private final MissionSeriesRepository missionSeriesRepository;
    private final ReportRepository reportRepository;
    private final MissionGenerator generator;
    private final Mapper mapper;
    private final MissionValidator validator;
    private final EmployeeRecommendationService employeeRecommendationService;
    private final MissionService missionService;
    private final ServerConstants messages;

    @Autowired
    public MissionResource(MissionRepository missionRepository,
                           MissionSeriesRepository missionSeriesRepository,
                           ReportRepository reportRepository,
                           MissionGenerator generator,
                           Mapper mapper,
                           MissionValidator validator,
                           EmployeeRecommendationService employeeRecommendationService,
                           MissionService missionService,
                           ServerConstants messages) {
        this.missionRepository = missionRepository;
        this.missionSeriesRepository = missionSeriesRepository;
        this.reportRepository = reportRepository;
        this.generator = generator;
        this.mapper = mapper;
        this.validator = validator;
        this.employeeRecommendationService = employeeRecommendationService;
        this.missionService = missionService;
        this.messages = messages;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public @ResponseBody
    List<MissionDto> find(@RequestParam Integer patientNumber,
                          @RequestParam @DateTimeFormat(pattern = HttpUtil.DATE_TIME_FORMAT) LocalDateTime startDate,
                          @RequestParam @DateTimeFormat(pattern = HttpUtil.DATE_TIME_FORMAT) LocalDateTime endDate) {
        List<Mission> missions = new ArrayList<>(missionRepository.findByPatientNumberAndIntersectingDateRange(patientNumber, startDate, endDate));
        List<MissionSeries> series = new ArrayList<>(missionSeriesRepository.findByPatientNumberAndIntersectingDateRange(patientNumber, startDate, endDate));
        List<Mission> temporaryMissions = generator.generateMissionsFromSeries(series, new DateRange(startDate, endDate));

        List<Mission> mergedMissions = missionService.mergeExistingMissionsWithTemporaryOnes(missions, temporaryMissions);

        List<MissionDto> result = mapper.map(mergedMissions, MissionDto.class);
        employeeRecommendationService.addEmployeeRecommendationsAvailableFlag(result, new DateRange(startDate, endDate));
        return result;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    @RequestMapping("/{id}")
    public @ResponseBody
    MissionDto get(@PathVariable Integer id) {
        Mission mission = missionRepository.findById(id).orElse(null);
        MissionDto result = mapper.map(mission, MissionDto.class);
        return result;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    @RequestMapping("/{missionId}/reports")
    public ReportDto getReport(@PathVariable Integer missionId) {
        Report entity = reportRepository.getByMissionId(missionId);
        if (entity == null) {
            return null;
        }
        return mapper.map(entity, ReportDto.class);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public void create(@RequestBody MissionDto dto) {
        Mission entity = mapper.map(dto, Mission.class);
        validator.validate(new EntityWrapper<>(null, entity));
        missionRepository.save(entity);
    }

}
