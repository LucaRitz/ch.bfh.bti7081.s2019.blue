package ch.bfh.bti7081.s2019.blue.client.app.home;

import ch.bfh.bti7081.s2019.blue.client.app.base.IsView;

public interface HomeView extends IsView {

    void setPresenter(Presenter presenter);

    interface Presenter {
    }
}
