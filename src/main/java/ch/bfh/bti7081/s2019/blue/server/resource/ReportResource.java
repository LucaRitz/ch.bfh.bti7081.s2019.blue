package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.ReportRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Report;
import ch.bfh.bti7081.s2019.blue.shared.dto.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;

import static ch.bfh.bti7081.s2019.blue.server.resource.ReportResource.PATH;

@RestController
@RequestMapping(PATH)
@Transactional
public class ReportResource {

    static final String PATH = "rest/reports";

    private final ReportRepository repository;
    private final Mapper mapper;

    @Autowired
    public ReportResource(ReportRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON)
    public void create(@RequestBody ReportDto dto) {
        Report entity = mapper.map(dto, Report.class);
        repository.save(entity);
    }
}
