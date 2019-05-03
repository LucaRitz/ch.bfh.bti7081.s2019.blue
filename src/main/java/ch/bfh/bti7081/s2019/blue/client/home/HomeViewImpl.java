package ch.bfh.bti7081.s2019.blue.client.home;

import ch.bfh.bti7081.s2019.blue.client.base.BaseViewImpl;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.stereotype.Component;

@HtmlImport("src/HomeViewImpl.html")
@Tag("home-view")
@Component
@UIScope
public class HomeViewImpl extends BaseViewImpl<TemplateModel> implements HomeView {

    @Id("welcomeText")
    private Label label;

    private Presenter presenter;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setText(String text) {
        label.setText(text);
    }
}
