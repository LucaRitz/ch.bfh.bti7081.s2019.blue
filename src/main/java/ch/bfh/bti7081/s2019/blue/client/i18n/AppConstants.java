package ch.bfh.bti7081.s2019.blue.client.i18n;

public enum AppConstants {

    MENU_HOME("menu.home"),
    MENU_PATIENTPLANNER("menu.patientplanner"),
    MENU_EMPLOYEEPLANNER("menu.employeeplanner"),
    MENU_EMPLOYEEDAILYOVERVIEW("menu.employeedailyoverview"),
    REPORT("report"),
    REPORT_TASKS("report.tasks"),
    REPORT_CONFIRMATION("report.confirmation"),
    FOOTER_TEXT("footer.text"),
    ACTION_SAVE("action.save"),
    ACTION_CANCEL("action.cancel"),
    MISSION_CREATE_START_DATE("mission.create.start.date"),
    MISSION_CREATE_START_TIME("mission.create.start.time"),
    MISSION_CREATE_END_DATE("mission.create.end.date"),
    MISSION_CREATE_END_TIME("mission.create.end.time"),
    MISSION_CREATE_REPETITION_TYPE("mission.create.repetition.type"),
    MISSION_EDIT_END_DATE("mission.edit.end.date"),
    MISSION_ASSIGN_EMPLOYEE("mission.assign.employee"),
    ASSIGN_TO_MISSION("assign.to.mission"),
    PATIENT_PLANNER_NO_SELECTED_MISSION("patient.planner.no.selected.mission"),
    PATIENT_PLANNER_HEALTH_VISITOR_ALREADY_ASSIGNED("patient.planner.health.visitor.already.assigned"),
    COLOR_RED_LEGEND( "legend.color.red"),
    COLOR_BLUE_LEGEND( "legend.color.blue"),
    ASSIGNED_TO_PATIENT_LEGEND( "legend.assigned.to.patient"),
    RECOMMONDATION_AVAILABLE_LEGEND( "legend.recommendation.available");

    private final String key;

    AppConstants(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
