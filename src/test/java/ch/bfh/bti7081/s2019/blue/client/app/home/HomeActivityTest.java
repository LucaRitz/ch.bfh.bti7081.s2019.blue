package ch.bfh.bti7081.s2019.blue.client.app.home;

import ch.bfh.bti7081.s2019.blue.client.ws.HomeService;
import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HomeActivityTest {

    private static final Random RAND = new Random();

    private HomeActivity activity;

    @Mock
    private HomeView view;
    @Mock
    private HomeService service;

    @BeforeEach
    void setUp() {
        activity = new HomeActivity(view, service);
    }

    @Test
    void loadMasterdata_updateView() {
        HomeDto expectedDto = new HomeDto();

        when(service.get()).thenReturn(CompletableFuture.completedFuture(expectedDto));

        // Act
        activity.loadMasterdata();

        // Assert
        verify(view).setData(expectedDto);
    }
}
