package ch.bfh.bti7081.s2019.blue.server.mapper;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import org.dozer.loader.api.BeanMappingBuilder;

import static org.dozer.loader.api.TypeMappingOptions.oneWay;

public class EmployeeMapping extends BeanMappingBuilder {
    @Override
    protected void configure() {
        mapping(Employee.class, EmployeeDto.class, oneWay());
    }
}
