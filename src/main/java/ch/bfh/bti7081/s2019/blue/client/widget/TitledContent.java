package ch.bfh.bti7081.s2019.blue.client.widget;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

@HtmlImport("src/TitledContent.html")
@Tag("titled-content")
public class TitledContent extends PolymerTemplate<TemplateModel> {

    @Id
    private Button backButton;

    public void showBackButton(boolean showButton) {
        backButton.getElement().setProperty("hidden", !showButton);
    }
}
