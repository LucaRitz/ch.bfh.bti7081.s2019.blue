package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.utils.EntityWrapper;
import ch.bfh.bti7081.s2019.blue.server.validator.MissionSeriesValidator;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.ResponseDto;
import ch.bfh.bti7081.s2019.blue.shared.service.MissionSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("rest/missionseries")
@Transactional
public class MissionSeriesResource implements MissionSeriesService {

    private final MissionSeriesRepository repository;
    private final Mapper mapper;
    private final MissionSeriesValidator validator;
    private final EntityManager em;
    private final ServerConstants messages;
    private final EntityManagerMixin emm;

    @Autowired
    public MissionSeriesResource(MissionSeriesRepository repository, Mapper mapper, MissionSeriesValidator validator,
                                 EntityManager em, ServerConstants messages, EntityManagerMixin emm) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.em = em;
        this.messages = messages;
        this.emm = emm;
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public ResponseDto<Void> create(MissionSeriesDto dto) {
        MissionSeries entity = mapper.map(dto, MissionSeries.class);
        List<String> errors = validator.validate(new EntityWrapper<>(null, entity));

        if (errors.isEmpty()) {
            repository.save(entity);
        }

        return new ResponseDto<>(errors);

    }

    @Override
    @DeleteMapping
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    @PatchMapping(produces = MediaType.APPLICATION_JSON, path = "/{id}")
    public ResponseDto<Void> updateEndDate(@PathVariable int id,
                                           @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        EntityWrapper<MissionSeries> wrapper = emm.get(id, repository);

        if (wrapper.isEmpty()) {
            return new ResponseDto<>(messages.entityNotFound(id));
        }
        wrapper.getModified().setEndDate(endDate);

        List<String> errors = validator.validate(wrapper);

        if (errors.isEmpty()) {
            em.merge(wrapper.getModified());
        }

        return new ResponseDto<>(errors);
    }
}