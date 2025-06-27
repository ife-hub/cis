package org.vaadin.example.layouts;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import lombok.extern.slf4j.Slf4j;
import org.vaadin.example.views.BlogView;
import org.vaadin.example.views.HomePage;

@Slf4j
public class Layout extends AppLayout {

    public Layout(){
        setClassName("homePage2");
        createHeader();
    }

    public void createHeader(){
        VerticalLayout vl = new VerticalLayout();
        vl.addClassName("homepage");
        vl.setSizeFull();
        getStyle().set("position", "relative");

        VerticalLayout topLayout = new VerticalLayout();
        topLayout.addClassName("topLayout");
        topLayout.setWidthFull();
        topLayout.setHeight("10vh");
        topLayout.getStyle()
                .set("background-color", "black")
                .set("position", "absolute")
                .set("top", "0")
                .set("left", "0");
        H1 topText = new H1("Free Deliveries for all orders above €60");
        topText.addClassName("topText");
        //RouterLink topLink = new RouterLink("Shop Now", SignUpView.class);
        //topLink.addClassName("topLink");
        //HorizontalLayout topHl = new HorizontalLayout(topText, topLink);
        //topLayout.add(topHl);
        topLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        VerticalLayout bottomLayout = new VerticalLayout();
        bottomLayout.addClassName("bottomLayout");
        HorizontalLayout hl2 = new HorizontalLayout();
        Image logo = new Image("images/aglogo.svg", "");
        logo.addClassName("aglogo2");
        HorizontalLayout logoHl = new HorizontalLayout(logo);
        logoHl.addClassName("logoHl");
        RouterLink link1 = new RouterLink("HOME", HomePage.class);
        RouterLink link2 = new RouterLink("BLOGS", BlogView.class);
//        RouterLink link3 = new RouterLink("ABOUT", AboutView.class);
//        RouterLink link4 = new RouterLink("SERVICE", ServicesView.class);
//        RouterLink link5 = new RouterLink("CONTACT US", ContactUs.class);
        link1.addClassName("rl"); link2.addClassName("rl");
        //link3.addClassName("rl"); link4.addClassName("rl"); link5.addClassName("rl");
        link1.getHighlightAction();
        hl2.add(link1, link2);
        hl2.addClassName("hl22");
        hl2.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        Image icon1 = new Image("icons/search.svg", "Search Icon");
        icon1.addClassName("navIcon");
        Image icon2 = new Image("icons/shopping.svg", "Shopping Icon");
        icon2.addClassName("navIcon");
        Image icon3 = new Image("icons/user.svg", "USer Icon");
        icon3.addClassName("navIcon");

        Button button1 = new Button(icon1);
        button1.addClassName("navButton");
        Button button2 = new Button(icon2);
        button2.addClassName("navButton");
        Button button3 = new Button(icon3);
        button3.addClassName("navButton");

        HorizontalLayout iconHl = new HorizontalLayout();
        iconHl.add(button1, button2, button3);
        iconHl.addClassName("iconHl");

        HorizontalLayout topNavHl = new HorizontalLayout();
        topNavHl.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        //topNavHl.add(logoHl, hl2, iconHl);
        topNavHl.add(hl2);
        topNavHl.addClassName("topNavHl");

        logoHl.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        hl2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        iconHl.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        bottomLayout.add(topNavHl);
        //bottomLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        bottomLayout.setWidthFull();
        //bottomLayout.setHeight("40vh");
        bottomLayout.setPadding(false);
        bottomLayout.setSpacing(false);
        bottomLayout.getStyle()
                .set("background-color", "white");
                //.set("border-radius", "20px 20px 0 0")
                //.set("position", "absolute")
                //.set("top", "48px")
                //.set("left", "0")
                //.set("margin-bottom", "10px");

        //vl.add(topLayout, bottomLayout);
        vl.add(bottomLayout);
        getStyle().set("min-height", "0 !important");
        addClassName("tt5");
        //getStyle().set("--_vaadin-app-layout-navbar-offset-size", "111px !important");
        addToNavbar(vl);
    }
}
