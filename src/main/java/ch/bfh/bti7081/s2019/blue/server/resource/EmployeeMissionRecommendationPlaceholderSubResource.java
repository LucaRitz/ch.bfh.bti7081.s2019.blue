package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.persistence.EmployeeRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.service.PatientMissionRecommendationService;
import ch.bfh.bti7081.s2019.blue.server.validator.ValidationException;
import ch.bfh.bti7081.s2019.blue.shared.HttpUtil;
import ch.bfh.bti7081.s2019.blue.shared.dto.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EmployeeResource.PATH + "/{employeeId}/misssionrecommendationplaceholders")
public class EmployeeMissionRecommendationPlaceholderSubResource {

    private final EmployeeRepository employeeRepository;
    private final PatientMissionRecommendationService patientMissionRecommendationService;
    private final ServerConstants serverConstants;

    @Autowired
    public EmployeeMissionRecommendationPlaceholderSubResource(EmployeeRepository employeeRepository,
                                                               PatientMissionRecommendationService patientMissionRecommendationService,
                                                               ServerConstants serverConstants) {
        this.employeeRepository = employeeRepository;
        this.patientMissionRecommendationService = patientMissionRecommendationService;
        this.serverConstants = serverConstants;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public @ResponseBody
    List<DateRange> find(@PathVariable Integer employeeId,
                         @RequestParam @DateTimeFormat(pattern = HttpUtil.DATE_TIME_FORMAT) LocalDateTime start,
                         @RequestParam @DateTimeFormat(pattern = HttpUtil.DATE_TIME_FORMAT) LocalDateTime end) {

        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (!employee.isPresent()) {
            throw new ValidationException(serverConstants.entityNotFound(employeeId));
        }

        return patientMissionRecommendationService
                .getPatientMissionRecommendationPlaceholders(employee.get(), new DateRange(start, end));
    }
}
