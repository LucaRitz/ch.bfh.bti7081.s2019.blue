package ch.bfh.bti7081.s2019.blue.client.i18n;

public enum AppConstants {

    MENU_HOME("menu.home"),
    MENU_PATIENTPLANNER("menu.patientplanner"),
    MENU_EMPLOYEEPLANNER("menu.employeeplanner"),
    FOOTER_TEXT("footer.text"),
    ACTION_NEXT("action.next"),
    ACTION_PREVIOUS("action.previous"),
    ACTION_SAVE("action.save"),
    ACTION_CANCEL("action.cancel"),
    MISSION_CREATE_START_DATE("mission.create.start.date"),
    MISSION_CREATE_START_TIME("mission.create.start.time"),
    MISSION_CREATE_END_DATE("mission.create.end.date"),
    MISSION_CREATE_END_TIME("mission.create.end.time"),
    MISSION_CREATE_REPETITION_TYPE("mission.create.repetition.type"),
    MISSION_CREATE_REPETITION_TYPE_ONCE("mission.create.repetition.type.once"),
    MISSION_CREATE_REPETITION_TYPE_DAILY("mission.create.repetition.type.daily"),
    MISSION_CREATE_REPETITION_TYPE_WEEKLY("mission.create.repetition.type.weekly"),
    MISSION_CREATE_REPETITION_TYPE_MONTHLY("mission.create.repetition.type.monthly"),
    MISSION_EDIT_END_DATE("mission.edit.end.date");

    private final String key;

    AppConstants(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
