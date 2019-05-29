package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.utils.EntityWrapper;
import ch.bfh.bti7081.s2019.blue.server.validator.MissionSeriesValidator;
import ch.bfh.bti7081.s2019.blue.server.validator.ValidationException;
import ch.bfh.bti7081.s2019.blue.shared.HttpUtil;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static ch.bfh.bti7081.s2019.blue.server.resource.MissionSeriesResource.PATH;

@RestController
@RequestMapping(PATH)
@Transactional
public class MissionSeriesResource {

    static final String PATH = "rest/missionseries";

    private final MissionSeriesRepository repository;
    private final Mapper mapper;
    private final MissionSeriesValidator validator;
    private final EntityManager em;
    private final ServerConstants messages;

    @Autowired
    public MissionSeriesResource(MissionSeriesRepository repository, Mapper mapper, MissionSeriesValidator validator,
                                 EntityManager em, ServerConstants messages) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.em = em;
        this.messages = messages;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public void create(@RequestBody MissionSeriesDto dto) {
        MissionSeries entity = mapper.map(dto, MissionSeries.class);
        validator.validate(new EntityWrapper<>(null, entity));
        repository.save(entity);
    }

    @DeleteMapping
    public void delete(int id) {
        repository.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public void updateEndDate(@PathVariable int id,
                                           @RequestParam  @DateTimeFormat(pattern = HttpUtil.DATE_TIME_FORMAT) LocalDateTime endDate) {
        Optional<MissionSeries> entityOpt = repository.findById(id);

        if (!entityOpt.isPresent()) {
            throw new ValidationException(messages.entityNotFound(id));
        }
        MissionSeries original = entityOpt.get();
        em.detach(original);

        MissionSeries modified = repository.getOne(id);
        modified.setEndDate(endDate);

        validator.validate(new EntityWrapper<>(original, modified));
    }
}
