package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.EmployeeRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.builder.EmployeeBuilder;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping(EmployeeResource.PATH)
public class EmployeeResource {

    static final String PATH = "rest/employees";

    private final EmployeeRepository repository;
    private final Mapper mapper;

    @Autowired
    public EmployeeResource(EmployeeRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public @ResponseBody List<EmployeeDto> find(@RequestParam EmployeeRole role) {
        Example<Employee> statement = new EmployeeBuilder().setRole(role).build();

        List<Employee> entities = repository.findAll(statement);
        return mapper.map(entities, EmployeeDto.class);
    }
}
