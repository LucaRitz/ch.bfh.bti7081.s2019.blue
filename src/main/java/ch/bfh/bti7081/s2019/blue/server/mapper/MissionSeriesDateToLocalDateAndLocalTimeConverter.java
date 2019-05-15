package ch.bfh.bti7081.s2019.blue.server.mapper;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import org.dozer.DozerConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class MissionSeriesDateToLocalDateAndLocalTimeConverter extends DozerConverter<MissionSeries, MissionSeriesDto> {

    public MissionSeriesDateToLocalDateAndLocalTimeConverter() {
        super(MissionSeries.class, MissionSeriesDto.class);
    }

    @Override
    public MissionSeriesDto convertTo(MissionSeries source, MissionSeriesDto destination) {

        LocalDateTime starDate = source
                .getStartDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime endDate = source
                .getEndDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        destination.setStartDate(starDate.toLocalDate());
        destination.setStartTime(starDate.toLocalTime());
        destination.setEndDate(endDate.toLocalDate());
        destination.setEndTime(endDate.toLocalTime());

        return destination;
    }

    @Override
    public MissionSeries convertFrom(MissionSeriesDto source, MissionSeries destination) {

        Date startDate = Date.from(
                LocalDateTime.of(source.getStartDate(), source.getStartTime())
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

        Date endDate = Date.from(
                LocalDateTime.of(source.getEndDate(), source.getEndTime())
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

        destination.setStartDate(startDate);
        destination.setEndDate(endDate);

        return destination;
    }
}
