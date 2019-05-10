package ch.bfh.bti7081.s2019.blue.server.persistence.builder;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;

public class EmployeeBuilder extends Builder<Employee> {

    public EmployeeBuilder() {
        super(new Employee());
    }

    public EmployeeBuilder setRole(EmployeeRole role) {
        entity.setRole(role);
        return this;
    }

}
