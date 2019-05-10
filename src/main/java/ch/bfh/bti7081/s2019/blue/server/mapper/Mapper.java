package ch.bfh.bti7081.s2019.blue.server.mapper;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Startup
@Singleton
@PermitAll
@Component
public class Mapper {

    private final DozerBeanMapper mapper = new DozerBeanMapper();

    @Inject
    private EntityManager em;

    @PostConstruct
    public void init() {
        final List<CustomConverter> converters = new ArrayList<>();
        mapper.setCustomConverters(converters);

        final Map<String, CustomConverter> idConverters = new HashMap<>();
        mapper.setCustomConvertersWithId(idConverters);

        // Mapping Konfigurationen
        mapper.addMapping(new HomeMapping());
        mapper.addMapping(new PatientMapping());
        mapper.addMapping(new EmployeeMapping());
        mapper.addMapping(new MissionMapping());
        mapper.addMapping(new MissionSeriesMapping());
    }

    public <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return mapper.map(source, destinationClass);
    }

    public void map(Object source, Object destination) {
        mapper.map(source, destination);
    }

    public <T> List<T> map(List<?> sourceList, Class<T> destType) {
        final List<T> resultList = new ArrayList<>();
        for (Object source : sourceList) {
            resultList.add(mapper.map(source, destType));
        }
        return resultList;
    }
}
