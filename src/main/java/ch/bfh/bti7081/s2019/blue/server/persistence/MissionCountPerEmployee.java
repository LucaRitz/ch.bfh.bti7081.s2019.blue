package ch.bfh.bti7081.s2019.blue.server.persistence;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Employee;

public class MissionCountPerEmployee {
    private final Long count;
    private final Employee employee;

    public MissionCountPerEmployee(Long count, Employee employee) {
        this.count = count;
        this.employee = employee;
    }

    public Long getCount() {
        return count;
    }

    public Employee getEmployee() {
        return employee;
    }
}
