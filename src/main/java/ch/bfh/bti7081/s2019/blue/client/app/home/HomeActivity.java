package ch.bfh.bti7081.s2019.blue.client.app.home;

import ch.bfh.bti7081.s2019.blue.client.app.base.BaseActivity;
import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class HomeActivity extends BaseActivity implements HomeView.Presenter {

    private final HomeView view;

    @Autowired
    public HomeActivity(HomeView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public IsView getView() {
        return view;
    }

    @Override
    public void start() {
        // do nothing
    }

}
