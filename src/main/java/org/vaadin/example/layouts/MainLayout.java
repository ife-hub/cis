package org.vaadin.example.layouts;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import org.vaadin.example.views.AddBlogView;
import org.vaadin.example.views.LoginView;
import org.vaadin.example.views.ReviewComments;

@Route("dashboard")
public class MainLayout extends AppLayout implements BeforeEnterObserver {

    public MainLayout()
    {
        createDrawer();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Check for "username" in Vaadin session
        if (VaadinSession.getCurrent().getAttribute("username") == null) {
            event.rerouteTo("login");
        }
    }

    private void createDrawer() {
        VerticalLayout general = new VerticalLayout();
        general.addClassName("general");
        general.setWidth("3000px");
        general.setWidthFull();
        VerticalLayout drawerItems = new VerticalLayout();
        Div div = new Div();
        div.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background-color", "blue")
                .set("height", "32px")
                .set("width", "32px")
                .set("border-radius", "5px");
        H1 h0 = new H1("CIS");
        h0.getStyle()
                .set("font-size", "15px")
                .set("color", "white");
        div.add(h0);
        H1 h1 = new H1("Check If Scam Dashboard");
        h1.getStyle()
                .set("font-size", "18px")
                .set("font-weight", "700");
        HorizontalLayout h00 = new HorizontalLayout(div, h1);
        h00.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        h00.getStyle()
                .set("margin-top", "2vh")
                .set("margin-bottom", "4vh");
        H1 h2 = new H1("Blogs");
        h2.getStyle()
                .set("font-weight", "100")
                .set("font-size", "19px");

        Image svgIcon1 = new Image("icons/dash.svg", "");
        RouterLink item1 = new RouterLink();
        Span text1 = new Span("Add a Blog");
        text1.addClassName("lay_txt1");
        item1.add(svgIcon1, text1);
        item1.setRoute(AddBlogView.class);
        item1.getStyle().set("text-decoration", "none")
                .set("color", "black").set("margin-left", "0.5vw").set("font-weight", "400").set("display", "flex").set("align-items", "center").set("gap", "0.5vw");
        item1.addClassName("itttt");

        Image svgIcon2 = new Image("icons/dash.svg", "");
        RouterLink item2 = new RouterLink();
        Span text2 = new Span("Review Comments"); text2.addClassName("lay_txt1");
        item2.add(svgIcon2, text2);
        item2.setRoute(ReviewComments.class);
        item2.getStyle().set("text-decoration", "none")
                .set("color", "black").set("margin-left", "0.5vw").set("font-weight", "400").set("display", "flex").set("align-items", "center").set("gap", "0.5vw");
        item2.addClassName("itttt");

        drawerItems.add(h00, h2, item1, item2);
        addToDrawer(drawerItems);
    }
}