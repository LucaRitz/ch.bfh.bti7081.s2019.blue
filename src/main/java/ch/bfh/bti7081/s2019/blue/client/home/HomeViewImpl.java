package ch.bfh.bti7081.s2019.blue.client.home;

import ch.bfh.bti7081.s2019.blue.client.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.stereotype.Component;

@HtmlImport("src/HomeViewImpl.html")
@Tag("home-view")
@Component
@UIScope
public class HomeViewImpl extends BaseViewImpl<TemplateModel> implements HomeView {

    @Id
    private Label title;
    @Id
    private Label welcomeText;

    private Presenter presenter;

    public HomeViewImpl() {
        title.setText(getTranslation(AppConstants.MENU_HOME.getKey()));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setText(String text) {
        welcomeText.setText(text);
    }

    @EventHandler
    private void infoButtonPressed() {
        Notification.show("Version 0.0.1-SNAPSHOT");
    }
}
