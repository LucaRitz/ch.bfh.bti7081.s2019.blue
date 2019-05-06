package ch.bfh.bti7081.s2019.blue.client.home;

import ch.bfh.bti7081.s2019.blue.client.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.base.IsView;
import ch.bfh.bti7081.s2019.blue.shared.dto.HomeDto;
import ch.bfh.bti7081.s2019.blue.shared.service.HomeService;
import com.google.common.annotations.VisibleForTesting;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@UIScope
public class HomeActivity extends BaseActivity implements HomeView.Presenter {

    private final HomeView view;
    private final HomeService homeService;

    @Inject
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
        HomeDto dto = homeService.get();
        view.setText(dto.getText());
    }
}
