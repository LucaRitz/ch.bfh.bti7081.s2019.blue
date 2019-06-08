package ch.bfh.bti7081.s2019.blue.server.mapper;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import org.dozer.loader.api.BeanMappingBuilder;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;

public class MissionMapping extends BeanMappingBuilder {
    @Override
    protected void configure() {
        mapping(Mission.class, MissionDto.class)
                .fields("startDate", "startDate", customConverter(LocalDateTimeConverter.class))
                .fields("endDate", "endDate", customConverter(LocalDateTimeConverter.class));
    }
}
