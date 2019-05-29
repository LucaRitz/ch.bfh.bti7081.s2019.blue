package ch.bfh.bti7081.s2019.blue.client.ws;

import ch.bfh.bti7081.s2019.blue.client.rest.IsRestService;
import ch.bfh.bti7081.s2019.blue.client.rest.Path;
import ch.bfh.bti7081.s2019.blue.client.rest.RestPromise;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Path("/rest/missionseries")
public interface MissionSeriesService extends IsRestService {

    @Path("/{missionSeriesId}")
    MissionSeriesEmployeeRecommendationSubService employeeRecommendations(@PathVariable Integer missionSeriesId);

    @PostMapping
    RestPromise<Void> create(@RequestBody MissionSeriesDto dto);

    @DeleteMapping
    @Path("/{id}")
    RestPromise<Void> delete(@PathVariable int id);

    @PutMapping
    @Path("/{id}")
    RestPromise<Void> updateEndDate(@PathVariable int id, @RequestParam("endDate") LocalDateTime endDate);
}
