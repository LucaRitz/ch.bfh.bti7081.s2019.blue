package ch.bfh.bti7081.s2019.blue.shared.service;

import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.ResponseDto;

import java.util.Date;

public interface MissionSeriesService {
    ResponseDto<Void> create(MissionSeriesDto dto);

    void delete(int id);

    ResponseDto<Void> updateEndDate(int id, Date endDate);
}
