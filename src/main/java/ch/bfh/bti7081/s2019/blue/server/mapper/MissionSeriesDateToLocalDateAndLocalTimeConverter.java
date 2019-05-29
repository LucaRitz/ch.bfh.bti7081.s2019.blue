package ch.bfh.bti7081.s2019.blue.server.mapper;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import org.dozer.DozerConverter;

import java.time.LocalDateTime;

public class MissionSeriesDateToLocalDateAndLocalTimeConverter extends DozerConverter<MissionSeries, MissionSeriesDto> {

    public MissionSeriesDateToLocalDateAndLocalTimeConverter() {
        super(MissionSeries.class, MissionSeriesDto.class);
    }

    @Override
    public MissionSeriesDto convertTo(MissionSeries source, MissionSeriesDto destination) {

        destination.setStartDate(source.getStartDate().toLocalDate());
        destination.setStartTime(source.getStartDate().toLocalTime());
        destination.setEndDate(source.getEndDate().toLocalDate());
        destination.setEndTime(source.getEndDate().toLocalTime());

        return destination;
    }

    @Override
    public MissionSeries convertFrom(MissionSeriesDto source, MissionSeries destination) {

        LocalDateTime startDate = LocalDateTime.of(source.getStartDate(), source.getStartTime());
        LocalDateTime endDate = LocalDateTime.of(source.getEndDate(), source.getEndTime());

        destination.setStartDate(startDate);
        destination.setEndDate(endDate);

        return destination;
    }
}
