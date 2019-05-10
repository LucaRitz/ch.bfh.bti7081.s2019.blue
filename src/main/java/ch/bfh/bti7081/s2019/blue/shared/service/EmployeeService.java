package ch.bfh.bti7081.s2019.blue.shared.service;

import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> findAllHealthVisitors();
}
