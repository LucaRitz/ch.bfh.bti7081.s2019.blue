package ch.bfh.bti7081.s2019.blue.client.i18n;

public enum AppConstants {

    MENU_HOME("menu.home"),
    MENU_PATIENTPLANNER("menu.patientplanner"),
    MENU_EMPLOYEEPLANNER("menu.employeeplanner"),
    FOOTER_TEXT("footer.text");

    private final String key;

    AppConstants(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
