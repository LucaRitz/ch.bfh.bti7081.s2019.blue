package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.MissionSeries;
import ch.bfh.bti7081.s2019.blue.server.utils.EntityWrapper;
import ch.bfh.bti7081.s2019.blue.server.validator.MissionSeriesValidator;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MissionSeriesServiceImplTest {
    private static final Random RAND = new Random();

    private MissionSeriesServiceImpl service;

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
    @Mock
    private EntityManagerMixin emm;

    @BeforeEach
    void setUp(){
        service = new MissionSeriesServiceImpl(repository, mapper, validator, em ,messages,emm);
    }

    @Test
    void create_createEntity() {
        MissionSeries expectedEntity = new MissionSeries();

        MissionSeriesDto dto = new MissionSeriesDto();
        when(mapper.map(dto, MissionSeries.class)).thenReturn(expectedEntity);

        // Act
        service.create(dto);

        // Assert
        verify(repository).save(expectedEntity);
    }

    @Test
    void delete_deleteEntity(){
        int expectedId = RAND.nextInt();

        // Act
        service.delete(expectedId);

        // Assert
        verify(repository).deleteById(expectedId);
    }

    @Test
    void updateEndDate_entityNotFound_getExpectedErrorMessage(){
        int expectedId = RAND.nextInt();

        String expectedMessage = String.valueOf(RAND.nextInt());
        when(messages.entityNotFound(expectedId)).thenReturn(expectedMessage);
        when(emm.get(expectedId,repository)).thenReturn(new EntityWrapper<>());

        // Act
        ResponseDto<Void> responseDto =  service.updateEndDate(new Date(), expectedId);

        // Assert
        assertTrue(responseDto.getErrors().contains(expectedMessage));
    }
}
