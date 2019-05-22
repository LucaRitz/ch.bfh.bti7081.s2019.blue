package ch.bfh.bti7081.s2019.blue.client.ws;

import ch.bfh.bti7081.s2019.blue.client.rest.ProvidesConverter;
import ch.bfh.bti7081.s2019.blue.shared.HttpUtil;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestConverter implements ProvidesConverter {

    static final String KEY_EMPLOYEE_DTO_LIST = "employeeDtoList";
    static final String KEY_MISSION_DTO_LIST = "missionDtoList";
    static final String KEY_PATIENT_REF_DTO_LIST = "patientRefDtoList";

    private static final Map<String, ParameterizedTypeReference<?>> CONVERTER = new HashMap<>();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(HttpUtil.DATE_TIME_FORMAT);

    static {
        CONVERTER.put(KEY_EMPLOYEE_DTO_LIST, new ParameterizedTypeReference<List<EmployeeDto>>() {
        });
        CONVERTER.put(KEY_MISSION_DTO_LIST, new ParameterizedTypeReference<List<MissionDto>>() {
        });
        CONVERTER.put(KEY_PATIENT_REF_DTO_LIST, new ParameterizedTypeReference<List<PatientRefDto>>() {
        });
    }

    @Override
    public ParameterizedTypeReference<?> getTypeReference(String key) {
        return CONVERTER.get(key);
    }

    @Override
    public Object convertParam(Object param) {
        if (param instanceof Date) {
            return DATE_FORMAT.format((Date) param);
        }

        return param;
    }
}
