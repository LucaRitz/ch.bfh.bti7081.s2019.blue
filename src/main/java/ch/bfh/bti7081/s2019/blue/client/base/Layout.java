package ch.bfh.bti7081.s2019.blue.client.base;

import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLayout;

@StyleSheet("styles/styles.css")
public class Layout extends Div implements RouterLayout {

    private final Div menu;
    private final Div content;
    private final Div footer;

    public Layout() {
        this.menu = new Div();
        this.content = new Div();

        this.footer = new Div();
        footer.getClassNames().add("footer");

        addMenuElement("home", "Home");

        this.footer.add(getTranslation(AppConstants.FOOTER_TEXT.getKey()));

        add(menu);
        add(content);
        add(footer);
    }

    private void addMenuElement(String target, String name) {
        Anchor anchor = new Anchor(target, name);
        this.menu.add(anchor);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.content.getElement().appendChild(content.getElement());
    }
}
