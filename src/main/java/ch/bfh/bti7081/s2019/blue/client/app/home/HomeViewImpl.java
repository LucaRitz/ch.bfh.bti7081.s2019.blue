package ch.bfh.bti7081.s2019.blue.client.app.home;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@HtmlImport("src/HomeViewImpl.html")
@Tag("home-view")
@Component
@UIScope
public class HomeViewImpl extends BaseViewImpl<HomeViewModel> implements HomeView {

    private Presenter presenter;

    public HomeViewImpl() {
        setText(getModel().getText()::setTitle, AppConstants.MENU_HOME);
        setText(getModel().getText()::setWelcome, AppConstants.HOME_WELCOME);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @EventHandler
    private void infoButtonPressed() {
        Notification.show("Version 1.0");
    }
}
