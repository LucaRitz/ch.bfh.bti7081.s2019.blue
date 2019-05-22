package ch.bfh.bti7081.s2019.blue.client.ws;

import ch.bfh.bti7081.s2019.blue.client.rest.IsRestService;
import ch.bfh.bti7081.s2019.blue.client.rest.Path;
import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.CompletableFuture;

@Path("/rest/home")
public interface HomeService extends IsRestService {

    @GetMapping
    CompletableFuture<HomeDto> get();
}
