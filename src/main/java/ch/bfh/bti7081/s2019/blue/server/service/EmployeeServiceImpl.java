package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.EmployeeRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.builder.EmployeeBuilder;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final Mapper mapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<EmployeeDto> findAllHealthVisitors() {
        EmployeeRole role = EmployeeRole.HEALTH_VISITOR;
        Example<Employee> statement = new EmployeeBuilder().setRole(role).build();

        List<Employee> entities = repository.findAll(statement);
        return mapper.map(entities, EmployeeDto.class);
    }
}
