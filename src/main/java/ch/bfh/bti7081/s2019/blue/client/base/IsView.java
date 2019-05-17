package ch.bfh.bti7081.s2019.blue.client.base;

import com.vaadin.flow.component.Component;

import java.util.Collection;

public interface IsView {
    Component asComponent();

    default void showNotification(String key) {
        showNotification(key, 3000);
    }

    void showNotification(String key, int duration);

    default void showTranslatedNotification(Collection<String> notificationTexts) {
        showTranslatedNotification(notificationTexts, 3000);
    }

    default void showTranslatedNotification(Collection<String> notificationTexts, int duration) {
        notificationTexts.forEach(text -> showTranslatedNotification(text, duration));
    }

    void showTranslatedNotification(String notificationText, int duration);
}
