package ch.bfh.bti7081.s2019.blue.server.service;

import ch.bfh.bti7081.s2019.blue.server.mapper.Mapper;
import ch.bfh.bti7081.s2019.blue.server.persistence.HomeRepository;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.Home;
import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HomeServiceImplTest {

    private HomeServiceImpl service;

    @Mock
    private HomeRepository repository;
    @Mock
    private Mapper mapper;

    @BeforeEach
    void setUp() {
        service = new HomeServiceImpl(repository, mapper);
    }

    @Test
    void get_homeNotFound_getEmpty() {
        when(repository.findOne(any(Example.class))).thenReturn(Optional.empty());

        // Act
        HomeDto result = service.get();

        // Assert
        assertNotNull(result);
        assertNull(result.getText());
    }

    @Test
    void get_homeFound_getExpected() {
        HomeDto expectedDto = new HomeDto();

        Home entity = new Home();
        when(repository.findOne(any())).thenReturn(Optional.of(entity));
        when(mapper.map(entity, HomeDto.class)).thenReturn(expectedDto);

        // Act
        HomeDto result = service.get();

        // Assert
        assertSame(expectedDto, result);
    }
}
