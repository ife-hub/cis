package org.vaadin.example.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.example.layouts.MainLayout;

@Route(value = "reviewComments", layout = MainLayout.class)
public class ReviewComments extends VerticalLayout {

    public ReviewComments(){

    }
}
