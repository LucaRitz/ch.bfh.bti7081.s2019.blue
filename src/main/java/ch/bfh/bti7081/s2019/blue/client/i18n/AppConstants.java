package ch.bfh.bti7081.s2019.blue.client.i18n;

public enum AppConstants {

    HOME_BUTTON("homeButton"),
    FOOTER_TEXT("footer.text");

    private final String key;

    AppConstants(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
