package ch.bfh.bti7081.s2019.blue.client.ws;

import ch.bfh.bti7081.s2019.blue.client.rest.IsRestService;
import ch.bfh.bti7081.s2019.blue.client.rest.Path;
import ch.bfh.bti7081.s2019.blue.client.rest.RestPromise;
import ch.bfh.bti7081.s2019.blue.client.rest.ReturnType;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Path("/rest/employees")
public interface EmployeeService extends IsRestService {

    @Path("/{employeeId}")
    EmployeeMissionSubService missions(@PathVariable Integer employeeId);

    @Path("/{employeeId}")
    EmployeeMissionRecommendationsSubService missionRecommondations(@PathVariable Integer employeeId);

    @Path("/{employeeId}")
    EmployeeMissionRecommendationPlaceholderSubService missionRecommondationPlaceholders(@PathVariable Integer employeeId);

    @GetMapping
    @ReturnType(RestConverter.KEY_EMPLOYEE_DTO_LIST)
    RestPromise<List<EmployeeDto>> find(@RequestParam("role") EmployeeRole role);
}
