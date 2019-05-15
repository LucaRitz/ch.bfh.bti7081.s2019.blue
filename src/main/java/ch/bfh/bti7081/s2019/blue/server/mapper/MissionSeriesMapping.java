package ch.bfh.bti7081.s2019.blue.server.mapper;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import static org.dozer.loader.api.TypeMappingOptions.oneWay;

public class MissionSeriesMapping extends BeanMappingBuilder {
    @Override
    protected void configure() {
        mapping(MissionSeries.class, MissionSeriesDto.class, oneWay())
                .exclude("startDate")
                .exclude("startTime")
                .exclude("endDate")
                .exclude("endTime")
                .fields("this", "this", FieldsMappingOptions.customConverter(MissionSeriesDateToLocalDateAndLocalTimeConverter.class));
    }
}
