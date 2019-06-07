package ch.bfh.bti7081.s2019.blue.server.mapper;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Report;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import org.dozer.loader.api.BeanMappingBuilder;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;

public class ReportMapping extends BeanMappingBuilder {
    @Override
    protected void configure() {
        mapping(Report.class, ReportDto.class)
                .fields("creationDate", "creationDate", customConverter(LocalDateTimeConverter.class))
                .fields("closedDate", "closedDate", customConverter(LocalDateTimeConverter.class));
    }
}
