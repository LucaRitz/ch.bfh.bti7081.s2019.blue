package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.PatientRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("rest/patients")
public class PatientResource {

    private final PatientRepository repository;
    private final Mapper mapper;

    @Autowired
    public PatientResource(PatientRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public @ResponseBody List<PatientRefDto> get() {
        List<Patient> entities = repository.findAll();
        return mapper.map(entities, PatientRefDto.class);
    }
}
