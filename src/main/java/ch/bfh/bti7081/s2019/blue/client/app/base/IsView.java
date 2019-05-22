package ch.bfh.bti7081.s2019.blue.client.app.base;

import com.vaadin.flow.component.Component;

public interface IsView {
    Component asComponent();

    default void showNotification(String key) {
        showNotification(key, 3000);
    }

    void showNotification(String key, int duration);
}
