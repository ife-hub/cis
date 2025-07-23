package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Value;

@Route("login")
public class LoginView extends VerticalLayout {

    @Value("${admin.username}")
    private String usernameString;

    @Value("${admin.password}")
    private String passwordString;

    public LoginView(){
//        TextField username = new TextField("Username");
//        PasswordField password = new PasswordField("Password");
//        Button loginButton = new Button("Login");
//
//        loginButton.addClickListener(e -> {
//            try {
//
//                if (username.getValue().equalsIgnoreCase(usernameString) && password.getValue().equalsIgnoreCase(passwordString)) {
//
//                    VaadinSession.getCurrent().setAttribute("username", username.getValue());
//
//                    UI.getCurrent().navigate("addBlog");
//                } else {
//                    Notification.show("Authentication failed");
//                }
//            } catch (Exception ex) {
//                Notification.show("Login error: " + ex.getMessage());
//            }
//        });
//
//        add(username, password, loginButton);

        addClassName("loginPage");
        setSizeFull();
        FormLayout fm = createFormLayout();
        add(fm);
    }

    public FormLayout createFormLayout(){
        FormLayout formLayout = new FormLayout();
        formLayout.addClassName("loginForm");

//        Image logo = new Image("images/logo.png", "Sign Up Logo");
//        logo.addClassName("form-logo");
        //logo.setWidthFull();

        TextField username  = new TextField();
        username.setLabel("Username:");
        username.setRequiredIndicatorVisible(true);

        PasswordField password = new PasswordField();
        password.setLabel("Password: ");
        password.setRequiredIndicatorVisible(true);

        Button submit = new Button("Submit");
        submit.addClassName("loginButton");

        submit.addClickListener(e -> {
            try {

                if (username.getValue().equalsIgnoreCase(usernameString) && password.getValue().equalsIgnoreCase(passwordString)) {

                    VaadinSession.getCurrent().setAttribute("username", username.getValue());

                    UI.getCurrent().navigate("dashboard");
                } else {
                    Notification.show("Authentication failed");
                }
            } catch (Exception ex) {
                Notification.show("Login error: " + ex.getMessage());
            }
        });

        formLayout.add(new VerticalLayout(username, password, submit));

        return formLayout;
    }
}
