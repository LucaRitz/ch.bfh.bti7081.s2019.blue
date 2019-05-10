package ch.bfh.bti7081.s2019.blue.client.home;

import ch.bfh.bti7081.s2019.blue.client.base.BaseViewImpl;
import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@HtmlImport("src/HomeViewImpl.html")
@Tag("home-view")
@Component
@UIScope
public class HomeViewImpl extends BaseViewImpl<HomeViewModel> implements HomeView {

    private final BeanValidationBinder<HomeDto> binder;

    @Id
    private TextField text;

    private Presenter presenter;

    public HomeViewImpl() {
        this.binder = new BeanValidationBinder<>(HomeDto.class);
        this.binder.forField(text).bind("text");

        getModel().setTitle(getTranslation(AppConstants.MENU_HOME.getKey()));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(HomeDto text) {
        binder.setBean(text);
        getModel().setHome(text);
    }

    @EventHandler
    private void infoButtonPressed() {
        BinderValidationStatus<HomeDto> status = binder.validate();
        if (!status.hasErrors()) {
            presenter.update(binder.getBean());
        }

        // presenter.update(newDto);
        Notification.show("Version 0.0.1-SNAPSHOT");
    }
}
