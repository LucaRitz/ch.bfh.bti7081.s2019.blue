package ch.bfh.bti7081.s2019.blue.client.ws;

import ch.bfh.bti7081.s2019.blue.client.rest.IsRestService;
import ch.bfh.bti7081.s2019.blue.client.rest.Path;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Path("/rest/missionseries")
public interface MissionSeriesService extends IsRestService {

    @Path("/{missionSeriesId}")
    MissionSeriesEmployeeRecommendationSubService employeeRecommendations(@PathVariable Integer missionSeriesId);

    @PostMapping
    CompletableFuture<Void> create(@RequestBody MissionSeriesDto dto);

    @DeleteMapping
    @Path("/{id}")
    CompletableFuture<Void> delete(@PathVariable int id);

    @PutMapping
    @Path("/{id}")
    CompletableFuture<Void> updateEndDate(@PathVariable int id, @RequestParam("endDate") Date endDate);
}
