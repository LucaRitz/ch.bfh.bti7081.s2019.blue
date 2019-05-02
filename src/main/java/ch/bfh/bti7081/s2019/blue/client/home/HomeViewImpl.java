package ch.bfh.bti7081.s2019.blue.client.home;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class HomeViewImpl extends VerticalLayout implements HomeView {

    private final Label label;

    private Presenter presenter;

    public HomeViewImpl() {
        label = new Label();
        add(label);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setText(String text) {
        label.setText(text);
    }

    @Override
    public Component asComponent() {
        return this;
    }
}
