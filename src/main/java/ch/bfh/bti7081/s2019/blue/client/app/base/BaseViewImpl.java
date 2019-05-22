package ch.bfh.bti7081.s2019.blue.client.app.base;

import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.function.Consumer;

public class BaseViewImpl<M extends TemplateModel> extends PolymerTemplate<M> {

    public Component asComponent() {
        return this;
    }

    public void showNotification(String key, int duration) {
        Notification notification = new Notification(getTranslation(key),duration);
        notification.open();
    }

    public void showTranslatedNotification(String notificationText, int duration) {
        Notification notification = new Notification(notificationText,duration);
        notification.open();
    }

    protected void setText(Consumer<String> consumer, AppConstants key) {
        consumer.accept(getTranslation(key.getKey()));
    }
}
