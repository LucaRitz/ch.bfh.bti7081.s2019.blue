package ch.bfh.bti7081.s2019.blue.client.ws;

import ch.bfh.bti7081.s2019.blue.client.rest.IsRestService;
import ch.bfh.bti7081.s2019.blue.client.rest.Path;
import ch.bfh.bti7081.s2019.blue.client.rest.RestPromise;
import ch.bfh.bti7081.s2019.blue.client.rest.ReturnType;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/rest/patients")
public interface PatientService extends IsRestService {

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    @ReturnType(RestConverter.KEY_PATIENT_DTO_LIST)
    RestPromise<List<PatientDto>> get();

    @Path("/{id}")
    @GetMapping(produces = MediaType.APPLICATION_JSON)
    RestPromise<PatientDto> get(@PathVariable("id") Integer id);
}
