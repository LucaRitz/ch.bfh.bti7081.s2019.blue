package ch.bfh.bti7081.s2019.blue.server.resource;

import ch.bfh.bti7081.s2019.blue.server.i18n.ServerConstants;
import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.PatientRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Patient;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientResourceTest {

    private PatientResource resource;

    @Mock
    private PatientRepository repository;
    @Mock
    private Mapper mapper;
    @Mock
    private ServerConstants messages;

    @BeforeEach
    void setUp() {
        resource = new PatientResource(repository, mapper, messages);
    }

    @Test
    void get_getExpected() {
        PatientDto expectedDto = new PatientDto();
        Patient entity = new Patient();
        when(repository.findAll()).thenReturn(Collections.singletonList(entity));
        when(mapper.map(Collections.singletonList(entity), PatientDto.class))
                .thenReturn(Collections.singletonList(expectedDto));

        // Act
        List<PatientDto> results = resource.get();

        // Assert
        assertTrue(results.contains(expectedDto));
    }
}
