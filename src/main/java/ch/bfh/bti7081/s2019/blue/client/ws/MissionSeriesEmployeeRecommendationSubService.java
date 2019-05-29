package ch.bfh.bti7081.s2019.blue.client.ws;

import ch.bfh.bti7081.s2019.blue.client.rest.IsRestService;
import ch.bfh.bti7081.s2019.blue.client.rest.Path;
import ch.bfh.bti7081.s2019.blue.client.rest.RestPromise;
import ch.bfh.bti7081.s2019.blue.client.rest.ReturnType;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Path("/employeerecommendations")
public interface MissionSeriesEmployeeRecommendationSubService extends IsRestService {

    @GetMapping
    @ReturnType(RestConverter.KEY_EMPLOYEE_DTO_LIST)
    RestPromise<List<EmployeeDto>> find(@RequestParam("start") LocalDateTime start, @RequestParam("end") LocalDateTime end);
}
