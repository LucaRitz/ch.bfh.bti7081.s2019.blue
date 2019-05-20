package ch.bfh.bti7081.s2019.blue.client.employee;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import ch.bfh.bti7081.s2019.blue.shared.service.EmployeeMissionSubService;
import ch.bfh.bti7081.s2019.blue.shared.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeePlannerActivityTest {

    private static final Random RAND = new Random();

    private EmployeePlannerActivity activity;

    @Mock
    private EmployeePlannerView view;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private EmployeeMissionSubService employeeMissionSubService;

    @BeforeEach
    void setUp() {
        activity = new EmployeePlannerActivity(view, employeeService, employeeMissionSubService);
    }

    @Test
    void start_loadMasterdata() {
        activity = spy(activity);

        // Act
        activity.start();

        // Assert
        verify(activity).loadMasterdata();
    }

    @Test
    void loadMasterdata_updateEmployeesOnView() {
        List<EmployeeDto> expectedEmployees = Collections.singletonList(new EmployeeDto());

        when(employeeService.find(EmployeeRole.HEALTH_VISITOR)).thenReturn(expectedEmployees);

        // Act
        activity.loadMasterdata();

        // Assert
        verify(view).setEmployees(expectedEmployees);
    }

    @Test
    void onSelectionChange_callServiceCorrectly() {
        int expectedEmployeeId = RAND.nextInt();
        Date expectedStartDate = new Date();
        Date expectedEndDate = new Date();

        EmployeeDto employeeDto = mock(EmployeeDto.class);
        when(employeeDto.getId()).thenReturn(expectedEmployeeId);

        // Act
        activity.onSelectionChange(employeeDto, expectedStartDate, expectedEndDate);

        // Assert
        verify(employeeMissionSubService).find(expectedEmployeeId, expectedStartDate, expectedEndDate);
    }

    @Test
    void onSelectionChange_updateMissionsOnView() {
        List<MissionDto> expectedMissions = Collections.singletonList(new MissionDto());

        when(employeeMissionSubService.find(any(), any(), any())).thenReturn(expectedMissions);

        // Act
        activity.onSelectionChange(new EmployeeDto(), new Date(), new Date());

        // Assert
        verify(view).setMissions(expectedMissions);
    }
}