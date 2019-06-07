package ch.bfh.bti7081.s2019.blue.client.app.base;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Navigation {

    public static final String HOME = "home";
    public static final String PATIENT_PLANNER = "patientplanner";
    public static final String EMPLOYEE_PLANNER = "employeeplanner";
    public static final String EMPLOYEE_DAILY_OVERVIEW = "employeedailyoverview";
    public static final String MISSION = "mission";
    public static final String REPORT = "report";

    private static final Map<String, Integer> ORDER_MAP = new HashMap<>();

    static {
        ORDER_MAP.put(HOME, 1);
        ORDER_MAP.put(PATIENT_PLANNER, 2);
        ORDER_MAP.put(EMPLOYEE_PLANNER, 3);
        ORDER_MAP.put(EMPLOYEE_DAILY_OVERVIEW, 4);

    }

    public static List<String> getRoutes() {
        return Stream.of(HOME, PATIENT_PLANNER, EMPLOYEE_PLANNER, EMPLOYEE_DAILY_OVERVIEW)
                .sorted(Comparator.comparing(Navigation::getOrder))
                .collect(Collectors.toList());
    }

    private static int getOrder(String navigationItem) {
        return ORDER_MAP.getOrDefault(navigationItem, 1);
    }

}
