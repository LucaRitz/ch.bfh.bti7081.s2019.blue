package ch.bfh.bti7081.s2019.blue.client.app.backoffice.employee;

import ch.bfh.bti7081.s2019.blue.client.app.backoffice.employee.assign.EmployeeAssignDialog;
import ch.bfh.bti7081.s2019.blue.client.rest.Promises;
import ch.bfh.bti7081.s2019.blue.client.ws.EmployeeService;
import ch.bfh.bti7081.s2019.blue.server.persistence.model.EmployeeRole;
import ch.bfh.bti7081.s2019.blue.shared.dto.EmployeeDto;
import ch.bfh.bti7081.s2019.blue.shared.dto.MissionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
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
    private EmployeeAssignDialog employeeAssignDialog;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        activity = new EmployeePlannerActivity(view, employeeService, employeeAssignDialog);
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

        when(employeeService.find(EmployeeRole.HEALTH_VISITOR))
                .thenReturn(Promises.fulfill(expectedEmployees));

        // Act
        activity.loadMasterdata();

        // Assert
        verify(view).setEmployees(expectedEmployees);
    }

    @Test
    void onSelectionChange_callServiceCorrectly() {
        int expectedEmployeeId = RAND.nextInt();
        LocalDateTime expectedStartDate = LocalDateTime.now();
        LocalDateTime expectedEndDate = LocalDateTime.now();

        EmployeeDto employeeDto = mock(EmployeeDto.class);
        when(employeeDto.getId()).thenReturn(expectedEmployeeId);

        // Act
        activity.onSelectionChange(employeeDto, expectedStartDate, expectedEndDate);

        // Assert
        verify(employeeService.missions(expectedEmployeeId)).find(expectedStartDate, expectedEndDate);
    }

    @Test
    void onSelectionChange_updateMissionsOnView() {
        List<MissionDto> expectedMissions = Collections.singletonList(new MissionDto());

        when(employeeService.missions(any()).find(any(), any()))
                .thenReturn(Promises.fulfill(expectedMissions));

        // Act
        activity.onSelectionChange(new EmployeeDto(), LocalDateTime.now(), LocalDateTime.now());

        // Assert
        verify(view).setMissions(expectedMissions);
    }
}
