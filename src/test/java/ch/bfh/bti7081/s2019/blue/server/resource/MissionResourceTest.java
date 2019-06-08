package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.MissionSeriesRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.ReportRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Mission;
import ch.bfh.bti7081.s2019.blue.server.service.EmployeeRecommendationService;
import ch.bfh.bti7081.s2019.blue.server.service.MissionService;
import ch.bfh.bti7081.s2019.blue.server.utils.MissionGenerator;
import ch.bfh.bti7081.s2019.blue.server.validator.MissionValidator;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MissionResourceTest {
    private static final Random RAND = new Random();

    private MissionResource resource;

    @Mock
    private MissionRepository repository;
    @Mock
    private MissionSeriesRepository seriesRepository;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private MissionGenerator generator;
    @Mock
    private Mapper mapper;
    @Mock
    private MissionValidator validator;
    @Mock
    private EmployeeRecommendationService employeeRecommendationService;
    @Mock
    private MissionService missionService;
    @Mock
    private ServerConstants messages;

    @BeforeEach
    void setUp(){
        resource = new MissionResource(
                repository,
                seriesRepository,
                reportRepository,
                generator,
                mapper,
                validator,
                employeeRecommendationService,
                missionService,
                messages
            );
    }

    @Test
    void create_createEntity() {
        Mission expectedEntity = new Mission();

        MissionDto dto = new MissionDto();
        when(mapper.map(dto, Mission.class)).thenReturn(expectedEntity);

        // Act
        resource.create(dto);

        // Assert
        verify(repository).save(expectedEntity);
    }
}
