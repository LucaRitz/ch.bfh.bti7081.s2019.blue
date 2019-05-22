package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.service.EmployeeRecommendationService;
import ch.bfh.bti7081.s2019.blue.server.validator.ValidationException;
import ch.bfh.bti7081.s2019.blue.shared.HttpUtil;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(MissionSeriesResource.PATH + "/{missionSeriesId}/employeerecommendations")
public class MissionSeriesEmployeeRecommendationSubResource {

    private final Mapper mapper;
    private final EmployeeRecommendationService employeeRecommendationService;
    private final MissionSeriesRepository missionSeriesRepository;
    private final ServerConstants serverConstants;

    @Autowired
    public MissionSeriesEmployeeRecommendationSubResource(Mapper mapper,
                                                          EmployeeRecommendationService employeeRecommendationService,
                                                          MissionSeriesRepository missionSeriesRepository,
                                                          ServerConstants serverConstants) {
        this.mapper = mapper;
        this.employeeRecommendationService = employeeRecommendationService;
        this.missionSeriesRepository = missionSeriesRepository;
        this.serverConstants = serverConstants;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public @ResponseBody
    List<EmployeeDto> find(@PathVariable Integer missionSeriesId,
                           @RequestParam @DateTimeFormat(pattern = HttpUtil.DATE_TIME_FORMAT) Date start,
                           @RequestParam @DateTimeFormat(pattern = HttpUtil.DATE_TIME_FORMAT) Date end) {

        Optional<MissionSeries> missionSeries = missionSeriesRepository.findById(missionSeriesId);
        if (!missionSeries.isPresent()) {
            throw new ValidationException(serverConstants.entityNotFound(missionSeriesId));
        }

        List<Employee> missions = employeeRecommendationService.getEmployeeRecommendations(missionSeries.get(), start, end);
        return mapper.map(missions, EmployeeDto.class);
    }
}
