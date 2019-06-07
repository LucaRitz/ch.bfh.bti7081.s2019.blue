package ch.bfh.bti7081.s2019.blue.client.ws;

import ch.bfh.bti7081.s2019.blue.client.rest.IsRestService;
import ch.bfh.bti7081.s2019.blue.client.rest.Path;
import ch.bfh.bti7081.s2019.blue.client.rest.RestPromise;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Path("/rest/reports")
public interface ReportService extends IsRestService {

    @PostMapping
    RestPromise<Void> create(@RequestBody ReportDto dto);
}
