package ch.bfh.bti7081.s2019.blue.client.app.base;

import ch.bfh.bti7081.s2019.blue.client.widget.UrlParameterEntryPoint;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class VaadinRouter implements IsRouter {
    @Override
    public <C, T> void navigate(Class<C> navigationTarget, T parameter) {
        if (UrlParameterEntryPoint.class.isAssignableFrom(navigationTarget) && parameter instanceof Integer) {
            UI.getCurrent().navigate(navigationTarget.asSubclass(UrlParameterEntryPoint.class), (Integer) parameter);
        }
    }

    @Override
    public <C> void navigate(Class<C> navigationTarget) {
        if (com.vaadin.flow.component.Component.class.isAssignableFrom(navigationTarget)) {
            UI.getCurrent().navigate(navigationTarget.asSubclass(com.vaadin.flow.component.Component.class));
        }
    }
}
