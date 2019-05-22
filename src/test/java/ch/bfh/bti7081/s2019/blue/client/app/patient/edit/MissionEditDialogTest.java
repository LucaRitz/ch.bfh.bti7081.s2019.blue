package ch.bfh.bti7081.s2019.blue.client.app.patient.edit;

import ch.bfh.bti7081.s2019.blue.client.app.base.DialogFactory;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsDialog;
import ch.bfh.bti7081.s2019.blue.client.ws.MissionSeriesService;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionSeriesDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissionEditDialogTest {

    private static final Random RAND = new Random();

    private MissionEditDialog dialog;

    @Mock
    private MissionEditView view;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private MissionSeriesService service;
    @Mock
    private DialogFactory dialogFactory;

    @BeforeEach
    void setUp() {
        this.dialog = new MissionEditDialog(view, service, dialogFactory);
    }

    @Test
    void start_editExpectedDto() {
        MissionSeriesDto expectedDto = new MissionSeriesDto();
        dialog.setProperties(expectedDto);

        // Act
        dialog.start();

        // Assert
        verify(view).edit(expectedDto);
    }

    @Test
    void start_showDialog() {
        // Act
        dialog.start();

        // Assert
        verify(dialogFactory).show(view);
    }

    @Test
    void onSaveClicked_callServiceCorrectly() {
        Date expectedDate = new Date();
        int expectedId = RAND.nextInt();
        MissionSeriesDto dto = mock(MissionSeriesDto.class);

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        when(dto.getEndDate()).thenReturn(date);
        when(dto.getEndTime()).thenReturn(time);
        when(dto.getId()).thenReturn(expectedId);

        dialog.dialog = mock(IsDialog.class);

        dialog = spy(dialog);
        doReturn(expectedDate).when(dialog).mergeDateTime(date, time);

        // Act
        dialog.onSaveClicked(dto);

        // Assert
        verify(service).updateEndDate(expectedId, expectedDate);
    }

    @Test
    void onSaveClicked_noErrors_callListenerAndClose() {
        when(service.updateEndDate(anyInt(), any())).thenReturn(CompletableFuture.completedFuture(null));
        dialog.dialog = mock(IsDialog.class);
        MissionEditDialog.Listener listener = mock(MissionEditDialog.Listener.class);
        dialog.setListener(listener);

        dialog = spy(dialog);
        doReturn(new Date()).when(dialog).mergeDateTime(any(), any());

        // Act
        dialog.onSaveClicked(mock(MissionSeriesDto.class, RETURNS_DEEP_STUBS));

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
