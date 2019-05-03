package ch.bfh.bti7081.s2019.blue.client.base;

import ch.bfh.bti7081.s2019.blue.client.i18n.AppConstants;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import java.util.List;

@StyleSheet("styles/styles.css")
public class
Layout extends Div implements RouterLayout {

    private final Div menu;
    private final Div content;
    private final Div footer;

    public Layout() {
        getClassNames().add("page");

        //MENU SECTION
        this.menu = new Div();
        this.menu.getClassNames().add("menu");
        generateMenuWithRegisteredRoutes();
        add(menu);

        //CONTENT SECTION
        this.content = new Div();
        this.content.getClassNames().add("content");

        add(content);


        //FOOTER SECTION
        this.footer = new Div();
        generateFooter();
        add(footer);
    }

    private void generateFooter() {
        this.footer.getClassNames().add("footer");
        this.footer.add("© " + getTranslation(AppConstants.FOOTER_TEXT.getKey()));

    }

    private void generateMenuWithRegisteredRoutes() {
        Router router = UI.getCurrent().getRouter();
        List<RouteData> routes = router.getRegistry().getRegisteredRoutes();

        this.menu.add(getLogoAnchor());

        Div menuItemWrapper = new Div();
        menuItemWrapper.getClassNames().add("menu-items");

        String currentRouteName = getCurrentRouteName();

        for (RouteData route : routes) {
            String url = route.getUrl();
            String translatedValue = getTranslation("menu." + url);

            Anchor anchor = new Anchor(url, translatedValue);
            anchor.getClassNames().add("menu-item");

            if (url.equals(currentRouteName))  {
                anchor.getClassNames().add("active-menu-item");
            }

            menuItemWrapper.getElement().appendChild(anchor.getElement());
        }

        this.menu.add(menuItemWrapper);
    }

    private String getCurrentRouteName() {
        VaadinServletRequest request = (VaadinServletRequest) VaadinService.getCurrentRequest();
        String currentUri = request.getRequestURL().toString();

        String currentRouteName = "";
        int routeIndex = currentUri.lastIndexOf("/");
        if (routeIndex > 0 && currentUri.length() >= routeIndex + 1) {
            currentRouteName = currentUri.substring(routeIndex + 1);
        }

        return currentRouteName;
    }

    private Anchor getLogoAnchor() {
        Anchor anchor = new Anchor("home");
        anchor.getClassNames().add("menu-logo");

        Image img = new Image("/icons/icon.png", "ICON");

        anchor.getElement().appendChild(img.getElement());

        return anchor;
    }


    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.content.getElement().appendChild(content.getElement());
    }
}
