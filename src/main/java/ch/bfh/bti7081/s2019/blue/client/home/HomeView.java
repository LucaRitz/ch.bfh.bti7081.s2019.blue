package ch.bfh.bti7081.s2019.blue.client.home;

import ch.bfh.bti7081.s2019.blue.client.base.IsView;

public interface HomeView extends IsView {

    void setPresenter(Presenter presenter);

    void setText(String text);

    interface Presenter {
    }
}
