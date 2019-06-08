package ch.bfh.bti7081.s2019.blue.client.app.home;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface HomeViewModel extends TemplateModel {
    Text getText();

    interface Text {
        void setTitle(String title);
        void setWelcome(String welcome);
    }
}
