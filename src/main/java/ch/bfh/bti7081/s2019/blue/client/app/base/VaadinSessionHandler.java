package ch.bfh.bti7081.s2019.blue.client.app.base;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class VaadinSessionHandler implements IsSessionHandler {
    @Override
    public <T> void set(String key, T parameter) {
        UI.getCurrent().getSession().setAttribute(key, parameter);
    }

    @Override
    public <T> T get(String key) {
        return (T) UI.getCurrent().getSession().getAttribute(key);
    }
}
