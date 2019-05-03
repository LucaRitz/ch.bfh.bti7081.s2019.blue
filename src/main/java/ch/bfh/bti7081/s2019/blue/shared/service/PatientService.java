package ch.bfh.bti7081.s2019.blue.shared.service;

import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;

import java.util.List;

public interface PatientService {

    List<PatientRefDto> findAll();
}
