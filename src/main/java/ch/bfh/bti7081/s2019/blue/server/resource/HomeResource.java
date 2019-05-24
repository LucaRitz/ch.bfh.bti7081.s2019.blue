package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.HomeRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.builder.HomeBuilder;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Home;
import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.Optional;

@RestController
@RequestMapping("rest/home")
public class HomeResource {

    private static final Logger LOG = LoggerFactory.getLogger(HomeResource.class.getName());

    private final HomeRepository repository;
    private final Mapper mapper;

    @Autowired
    public HomeResource(HomeRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public HomeDto get() {
        LOG.info("Custom Logging");
        Example<Home> statement = new HomeBuilder().setReference(10L).build();
        Optional<Home> home = repository.findOne(statement);

        if (home.isPresent()) {
            Home entity = home.get();
            return mapper.map(entity, HomeDto.class);
        }
        return new HomeDto();
    }
}
