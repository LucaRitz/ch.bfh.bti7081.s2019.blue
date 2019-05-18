package ch.bfh.bti7081.s2019.blue.client.patient.create;

import ch.bfh.bti7081.s2019.blue.client.base.DialogFactory;
import ch.bfh.bti7081.s2019.blue.client.base.IsDialog;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.RepetitionType;
import ch.bfh.bti7081.s2019.blue.shared.dto.ResponseDto;
import ch.bfh.bti7081.s2019.blue.shared.service.MissionSeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissionCreateDialogTest {

    private static final Random RAND = new Random();

    private MissionCreateDialog dialog;

    @Mock
    private MissionCreateView view;
    @Mock
    private MissionSeriesService service;
    @Mock
    private DialogFactory dialogFactory;

    @BeforeEach
    void setUp() {
        this.dialog = new MissionCreateDialog(view, service, dialogFactory);
    }

    @Test
    void start_editViewWithFilledDto() {
        PatientRefDto expectedPatient = new PatientRefDto();
        dialog.setProperties(expectedPatient);

        // Act
        dialog.start();

        // Assert
        ArgumentCaptor<MissionSeriesDto> captor = ArgumentCaptor.forClass(MissionSeriesDto.class);
        verify(view).edit(captor.capture());

        MissionSeriesDto missionSeriesDto = captor.getValue();
        assertNotNull(missionSeriesDto.getEndDate());
        assertNotNull(missionSeriesDto.getEndTime());
        assertNotNull(missionSeriesDto.getStartDate());
        assertNotNull(missionSeriesDto.getStartTime());
        assertEquals(expectedPatient, missionSeriesDto.getPatient());
        assertEquals(RepetitionType.ONCE, missionSeriesDto.getRepetitionType());
        assertNull(missionSeriesDto.getId());
    }

    @Test
    void start_showDialog() {
        // Act
        dialog.start();

        // Assert
        verify(dialogFactory).show(view);
    }

    @Test
    void onSaveClicked_hasErrors_showErrors() {
        String expectedError = String.valueOf(RAND.nextLong());

        MissionSeriesDto dto = new MissionSeriesDto();
        when(service.create(dto)).thenReturn(new ResponseDto<>(expectedError));

        // Act
        dialog.onSaveClicked(dto);

        // Assert
        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);
        verify(view).showTranslatedNotification(captor.capture());
        assertTrue(captor.getValue().contains(expectedError));
    }

    @Test
    void onSaveClicked_noErrors_callListenerAndClose() {
        MissionCreateDialog.Listener listener = mock(MissionCreateDialog.Listener.class);
        dialog.setListener(listener);
        dialog.dialog = mock(IsDialog.class);

        MissionSeriesDto dto = new MissionSeriesDto();
        when(service.create(dto)).thenReturn(new ResponseDto<>());

        // Act
        dialog.onSaveClicked(dto);

        // Assert
        verify(listener).onSaved();
        verify(dialog.dialog).close();
    }

    @Test
    void onCancelClicked_dialogClosed() {
        dialog.dialog = mock(IsDialog.class);

        // Act
        dialog.onCancelClicked();

        // Assert
        verify(dialog.dialog).close();
    }
}
