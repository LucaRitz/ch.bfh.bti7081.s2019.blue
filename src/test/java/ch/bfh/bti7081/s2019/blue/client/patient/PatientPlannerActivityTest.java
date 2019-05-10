package ch.bfh.bti7081.s2019.blue.client.patient;

import ch.bfh.bti7081.s2019.blue.shared.dto.PatientRefDto;
import ch.bfh.bti7081.s2019.blue.shared.service.MissionService;
import ch.bfh.bti7081.s2019.blue.shared.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientPlannerActivityTest {

    private PatientPlannerActivity activity;

    @Mock
    private PatientPlannerView view;
    @Mock
    private PatientService patientService;
    @Mock
    private MissionService missionService;

    @BeforeEach
    void setUp() {
        activity = new PatientPlannerActivity(view, patientService, missionService);
    }

    @Test
    void loadMasterdata_updateView() {
        List<PatientRefDto> expectedPatients = Collections.singletonList(new PatientRefDto());
        when(patientService.findAll()).thenReturn(expectedPatients);

        // Act
        activity.loadMasterdata();

        // Assert
        verify(view).setPatients(expectedPatients);
    }
}
