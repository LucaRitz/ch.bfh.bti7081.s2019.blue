package ch.bfh.bti7081.s2019.blue.client.i18n;

public enum AppConstants {

    MENU_HOME("menu.home"),
    MENU_PATIENTPLANNER("menu.patientplanner"),
    MENU_EMPLOYEEPLANNER("menu.employeeplanner"),
    MENU_EMPLOYEEDAILYOVERVIEW("menu.employeedailyoverview"),
    REPORT("report"),
    REPORT_TASKS("report.tasks"),
    REPORT_TASKS_DESCRIPTION("report.tasks.description"),
    REPORT_TASKS_TASKDESCRIPTION("report.tasks.taskdescription"),
    REPORT_TASKS_AT_LEAST_ONE_TASK("report.tasks.error.atleastonetask"),
    REPORT_ACTIONS("report.actions"),
    REPORT_ACTIONS_DESCRIPTION("report.actions.description"),
    REPORT_ACTIONS_ACTIONDESCRIPTION("report.actions.actiondescription"),
    REPORT_CONFIRMATION("report.confirmation"),
    REPORT_HEALTHSTATUS("report.healthstatus"),
    REPORT_HEALTHSTATUS_PHYSICAL("report.healthstatus.physical"),
    REPORT_HEALTHSTATUS_PSYCHOLOGICAL("report.healthstatus.psychological"),
    FOOTER_TEXT("footer.text"),
    ACTION_SAVE("action.save"),
    ACTION_CANCEL("action.cancel"),
    ACTION_ADD("action.add"),
    MISSION("mission"),
    MISSION_DETAILS("mission.details"),
    MISSION_CREATE_START_DATE("mission.create.start.date"),
    MISSION_CREATE_START_TIME("mission.create.start.time"),
    MISSION_CREATE_END_DATE("mission.create.end.date"),
    MISSION_CREATE_END_TIME("mission.create.end.time"),
    MISSION_CREATE_REPETITION_TYPE("mission.create.repetition.type"),
    MISSION_EDIT_END_DATE("mission.edit.end.date"),
    MISSION_START("mission.start"),
    MISSION_START_DATE("mission.start.date"),
    MISSION_END("mission.end"),
    MISSION_END_DATE("mission.end.date"),
    MISSION_ASSIGN_EMPLOYEE("mission.assign.employee"),
    ASSIGN_TO_MISSION("assign.to.mission"),
    PATIENT_NAME("patient"),
    PATIENT_LOCATION("patient.location"),
    PATIENT_LOCATION_OPEN_IN_MAPS("patient.location.open.in.map"),
    PATIENT_MEDICATION("patient.medication"),
    PATIENT_DOCTOR("patient.doctor"),
    PATIENT_NO_MEDICATION("patient.no.medication"),
    PATIENT_PLANNER_NO_SELECTED_MISSION("patient.planner.no.selected.mission"),
    PATIENT_PLANNER_HEALTH_VISITOR_ALREADY_ASSIGNED("patient.planner.health.visitor.already.assigned"),
    COLOR_RED_LEGEND( "legend.color.red"),
    COLOR_BLUE_LEGEND( "legend.color.blue"),
    ASSIGNED_TO_PATIENT_LEGEND( "legend.assigned.to.patient"),
    RECOMMONDATION_AVAILABLE_LEGEND( "legend.recommendation.available"),
    REQUIRED("required");

    private final String key;

    AppConstants(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
