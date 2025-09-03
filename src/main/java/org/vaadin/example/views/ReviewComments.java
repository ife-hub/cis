package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.vaadin.example.entities.Blog;
import org.vaadin.example.layouts.MainLayout;
import org.vaadin.example.services.BlogService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Route(value = "reviewComments", layout = MainLayout.class)
public class ReviewComments extends VerticalLayout implements BeforeEnterObserver {

    private final BlogService blogService;

    public ReviewComments(BlogService blogService){
        this.blogService = blogService;

        add(getOtherBlogs());
        addClassName("bl0");
        setPadding(false);
        setSpacing(false);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Check for "username" in Vaadin session
        if (VaadinSession.getCurrent().getAttribute("username") == null) {
            event.rerouteTo("login");
        }
    }

    public VerticalLayout getOtherBlogs(){
        VerticalLayout vl = new VerticalLayout(); vl.addClassName("ob_vl");

        List<Blog> allBlogs = blogService.getAllBlogs();

        H1 h00 = new H1("Select a Blog to Review Comments");
        h00.getStyle().set("font-weight", "400").set("margin-top", "2vh").set("margin-bottom", "5vh");
        vl.add(h00);

        List<Blog> otherBlogs = new ArrayList<>();

        int j = 0;

        for(int i=0; i<allBlogs.size(); i++){

            Blog blogItem = allBlogs.get(i);

            otherBlogs.add(blogItem);

            if ( (otherBlogs.size() == 3) || i == allBlogs.size() - 1){
                HorizontalLayout hl = new HorizontalLayout(); hl.addClassName("ob_hl");

                for (Blog bl : otherBlogs){
                    Div div = new Div(); div.addClassName("ob_div");

                    Image image = new Image(); image.addClassName("ob_image");
                    image.setSrc(bl.getImageUrl());
                    image.setAlt(bl.getTitle());

                    H1 h1 = new H1(bl.getTitle()); h1.addClassName("ob_h1");
                    H1 h2 = new H1(bl.getDescription()); h2.addClassName("ob_h2");

                    Div div2 = new Div(); div2.addClassName("ob_div2");

                    H1 h3 = new H1(bl.getAuthor() + " "); h3.addClassName("ob_h3");
                    LocalDateTime date = bl.getCreateDate();
                    String dateString = "-  " +String.valueOf(date.getDayOfMonth()) + " " + String.valueOf(date.getMonth()) + " " + String.valueOf(date.getYear());
                    H1 h4 = new H1(dateString); h4.addClassName("ob_h4");

                    div2.add(h3, h4);

                    div.add(image, h1, h2, div2);

                    div.getStyle().set("cursor", "pointer");

                    AtomicReference<Blog> blogRef = new AtomicReference<>();
                    blogRef.set(bl);

                    div.addClickListener(e -> {
                        UI.getCurrent().navigate("reviewComments2/" + blogRef.get().getBlogId());
                    });

                    hl.add(div);
                    j++;
                }
                vl.add(hl);
                otherBlogs.clear();
            }
        }

        return vl;
    }
}
