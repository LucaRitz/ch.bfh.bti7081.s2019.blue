package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.PatientRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;
import ch.bfh.bti7081.s2019.blue.server.validator.ValidationException;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("rest/patients")
public class PatientResource {

    private final PatientRepository repository;
    private final Mapper mapper;
    private final ServerConstants messages;

    @Autowired
    public PatientResource(PatientRepository repository, Mapper mapper, ServerConstants messages) {
        this.repository = repository;
        this.mapper = mapper;
        this.messages = messages;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public @ResponseBody List<PatientDto> get() {
        List<Patient> entities = repository.findAll();
        return mapper.map(entities, PatientDto.class);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON)
    public @ResponseBody
    PatientDto get(@PathVariable("id") Integer id) {
        Patient entity = repository.findById(id)
                .orElseThrow(() -> new ValidationException(messages.entityNotFound(id)));
        return mapper.map(entity, PatientDto.class);
    }

}
