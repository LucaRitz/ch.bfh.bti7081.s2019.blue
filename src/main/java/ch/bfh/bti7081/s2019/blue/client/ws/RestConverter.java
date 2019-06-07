package ch.bfh.bti7081.s2019.blue.client.ws;

import ch.bfh.bti7081.s2019.blue.client.rest.ProvidesConverter;
import ch.bfh.bti7081.s2019.blue.shared.HttpUtil;
import ch.bfh.bti7081.s2019.blue.shared.dto.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestConverter implements ProvidesConverter {

    static final String KEY_EMPLOYEE_DTO_LIST = "employeeDtoList";
    static final String KEY_MISSION_DTO_LIST = "missionDtoList";
    static final String KEY_PATIENT_DTO_LIST = "patientDtoList";
    static final String KEY_DATE_RANGE_LIST = "dateRangeList";

    private static final Map<String, ParameterizedTypeReference<?>> CONVERTER = new HashMap<>();

    static {
        CONVERTER.put(KEY_EMPLOYEE_DTO_LIST, new ParameterizedTypeReference<List<EmployeeDto>>() {});
        CONVERTER.put(KEY_MISSION_DTO_LIST, new ParameterizedTypeReference<List<MissionDto>>() {});
        CONVERTER.put(KEY_PATIENT_DTO_LIST, new ParameterizedTypeReference<List<PatientDto>>() {});
        CONVERTER.put(KEY_DATE_RANGE_LIST, new ParameterizedTypeReference<List<DateRange>>() {});
    }

    @Override
    public ParameterizedTypeReference<?> getTypeReference(String key) {
        return CONVERTER.get(key);
    }

    @Override
    public Object convertParam(Object param) {
        if (param instanceof LocalDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(HttpUtil.DATE_TIME_FORMAT);
            return formatter.format((LocalDateTime) param);
        }

        return param;
    }
}
