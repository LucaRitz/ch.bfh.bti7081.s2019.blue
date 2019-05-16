package ch.bfh.bti7081.s2019.blue.client.base;

import com.vaadin.flow.component.Component;

public interface IsView {
    Component asComponent();

    void showNotification(String notificationText, int duration);
}
