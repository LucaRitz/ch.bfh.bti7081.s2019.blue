package ch.bfh.bti7081.s2019.blue.client.base;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

public class BaseViewImpl<M extends TemplateModel> extends PolymerTemplate<M> {

    public Component asComponent() {
        return this;
    }
}
