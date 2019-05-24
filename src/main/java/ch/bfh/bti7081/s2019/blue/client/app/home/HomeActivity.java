package ch.bfh.bti7081.s2019.blue.client.app.home;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import ch.bfh.bti7081.s2019.blue.client.ws.HomeService;
import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class HomeActivity extends BaseActivity implements HomeView.Presenter {

    private final HomeView view;
    private final HomeService homeService;

    @Autowired
    public HomeActivity(HomeView view, HomeService homeService) {
        this.view = view;
        this.view.setPresenter(this);
        this.homeService = homeService;

    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {
        loadMasterdata();
    }

    @VisibleForTesting
    void loadMasterdata() {
        homeService.get()
                .done(view::setData);
    }

    @Override
    public void update(HomeDto homeDto) {
        Notification.show(homeDto.getText());
    }


}
