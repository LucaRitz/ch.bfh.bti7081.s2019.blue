package ch.bfh.bti7081.s2019.blue.server.mapper;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import org.dozer.loader.api.BeanMappingBuilder;

import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.TypeMappingOptions.oneWay;

public class PatientMapping extends BeanMappingBuilder {
    @Override
    protected void configure() {
        mapping(Patient.class, PatientRefDto.class)
                .fields("birthdate", "birthdate", customConverter(LocalDateTimeConverter.class));

        mapping(Patient.class, PatientDto.class)
                .fields("birthdate", "birthdate", customConverter(LocalDateTimeConverter.class));
    }
}
