package ch.bfh.bti7081.s2019.blue.client.ws;

import ch.bfh.bti7081.s2019.blue.client.rest.IsRestService;
import ch.bfh.bti7081.s2019.blue.client.rest.Path;
import ch.bfh.bti7081.s2019.blue.client.rest.ReturnType;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Path("/rest/missions")
public interface MissionService extends IsRestService {

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    @ReturnType(RestConverter.KEY_MISSION_DTO_LIST)
    CompletableFuture<List<MissionDto>> find(@RequestParam("patientNumber") Integer patientNumber,
                                            @RequestParam("startDate") Date startDate,
                                            @RequestParam("endDate") Date endDate);
}
