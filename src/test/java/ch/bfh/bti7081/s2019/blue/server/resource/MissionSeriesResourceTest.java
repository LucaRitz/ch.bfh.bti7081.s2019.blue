package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.validator.MissionSeriesValidator;
import ch.bfh.bti7081.s2019.blue.server.validator.ValidationException;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MissionSeriesResourceTest {
    private static final Random RAND = new Random();

    private MissionSeriesResource resource;

    @Mock
    private MissionSeriesRepository repository;
    @Mock
    private Mapper mapper;
    @Mock
    private MissionSeriesValidator validator;
    @Mock
    private EntityManager em;
    @Mock
    private ServerConstants messages;

    @BeforeEach
    void setUp() {
        resource = new MissionSeriesResource(repository, mapper, validator, em, messages);
    }

    @Test
    void create_createEntity() {
        MissionSeries expectedEntity = new MissionSeries();

        MissionSeriesDto dto = new MissionSeriesDto();
        when(mapper.map(dto, MissionSeries.class)).thenReturn(expectedEntity);

        // Act
        resource.create(dto);

        // Assert
        verify(repository).save(expectedEntity);
    }

    @Test
    void delete_deleteEntity() {
        int expectedId = RAND.nextInt();

        // Act
        resource.delete(expectedId);

        // Assert
        verify(repository).deleteById(expectedId);
    }

    @Test
    void updateEndDate_entityNotFound_getExpectedErrorMessage() {
        // Act/Assert
        assertThrows(ValidationException.class, () -> resource.updateEndDate(RAND.nextInt(), LocalDateTime.now()));
    }
}
