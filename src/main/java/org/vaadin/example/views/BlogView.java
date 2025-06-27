package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.vaadin.example.entities.Blog;
import org.vaadin.example.layouts.Layout;
import org.vaadin.example.services.BlogService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route(value="blogView", layout= Layout.class)
public class BlogView extends VerticalLayout {

    private final BlogService blogService;

    public BlogView(BlogService blogService){
        this.blogService = blogService;
        add(getVl(), getVl6());
        addClassName("bl0");
        setPadding(false);
        setSpacing(false);
    }

    public VerticalLayout getVl(){
        VerticalLayout vl = new VerticalLayout();
        List<Blog> blogList = new ArrayList<>();

        blogList = blogService.getAllBlogs();

        Blog firstBlog = null;
        if (!blogList.isEmpty()) {
            firstBlog = blogList.get(0);
        }

        String firstImageUrl = firstBlog.getImageUrl();

        String largeUrl = firstImageUrl.replace("/upload/", "/upload/w_1920,h_1080,c_fill,q_auto/");


        Div firDiv = new Div();
        firDiv.addClassName("firDiv");
        String firBg = "rgba(0, 0, 0, 0.7) url('" + largeUrl + "')";
        firDiv.getStyle().set("background", firBg);

        H1 h1 = new H1(firstBlog.getAuthor()); h1.addClassName("bl_h1");
        H1 h2 = new H1(firstBlog.getTitle()); h2.addClassName("bl_h2");
        H1 h3 = new H1(firstBlog.getDescription()); h3.addClassName("bl_h3");

        firDiv.add(h2, h3, h1);

        firDiv.addClickListener(e -> {
            UI.getCurrent().navigate(HomePage.class);
        });

        H1 h4 = new H1("Recent Blog Posts"); h4.addClassName("bl_h4");

        Button viewMoreBlogs = new Button("View More Blogs");
        viewMoreBlogs.addClassName("bl_viewMoreBlogs");
        viewMoreBlogs.addClickListener(e -> {
            UI.getCurrent().navigate(AllBlogsView.class);
        });

        vl.add(firDiv, h4, getOtherBlogs(), viewMoreBlogs);
        return vl;
    }

    public VerticalLayout getOtherBlogs(){
        VerticalLayout vl = new VerticalLayout(); vl.addClassName("ob_vl");

        List<Blog> allBlogs = blogService.getAllBlogs();
        Blog firBlog = allBlogs.get(0);
        allBlogs.remove(firBlog);

        List<Blog> otherBlogs = new ArrayList<>();

        int j = 0;

        for(int i=0; i<allBlogs.size(); i++){
            //System.out.println(i);
            Blog blogItem = allBlogs.get(i);

//            if( ((i-1) % 3) == 0){
//                otherBlogs.clear();
//                otherBlogs.add(blogItem);
//            } else {
//                otherBlogs.add(blogItem);
//            }

            otherBlogs.add(blogItem);

            if ( (otherBlogs.size() == 3) || i == allBlogs.size() - 1){
                HorizontalLayout hl = new HorizontalLayout(); hl.addClassName("ob_hl");

                for (Blog bl : otherBlogs){
                    //System.out.println(j);
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
                    div.addClickListener(e -> {
                        UI.getCurrent().navigate(HomePage.class);
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

    public VerticalLayout getVl6(){
        VerticalLayout vl = new VerticalLayout(); vl.addClassName("h4_vl");
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        FlexLayout fl1 = new FlexLayout();

        Div dv1 = new Div(); dv1.addClassName("h4_dv1");
        //Image h11 = new Image("images/WhiteLogo.png", "Logo"); h11.addClassName("h4_h11");
        H1 h12 = new H1("CheckIfScam"); h12.addClassName("h4_h12");
        H1 h13 = new H1("Subscribe to Our Newsletter"); h13.addClassName("h4_h12");
        //H1 h13 = new H1("Get 10% off your first order"); h13.addClassName("h4_h13");
        TextField h14 = new TextField(); h14.addClassName("h4_h14");
        h14.setPlaceholder("Enter your email");
        Image icon1 = new Image("icons/send.svg", "Send Icon");
        icon1.addClassName("h4_icon1");
        Button button1 = new Button(icon1); button1.addClassName("h4_button1");
        h14.setSuffixComponent(button1);
        VerticalLayout vv1 = new VerticalLayout(); vv1.addClassName("h4_vv1");
        vv1.add(h12, h13, h14);
        dv1.add(vv1);

        Div dv2 = new Div(); dv2.addClassName("h4_dv2");
        H1 h21 = new H1("Support"); h21.addClassName("h4_h21");
        H1 h22 = new H1("38 Francis Road"); h22.addClassName("h4_h22");
        H1 h23 = new H1("Victoria Island"); h23.addClassName("h4_h23");
        H1 h24 = new H1("Lagos State"); h24.addClassName("h4_h24");
        //H1 h25 = new H1("B276LX"); h25.addClassName("h4_h25");
        H1 h26 = new H1("info@checkifscam.ng"); h26.addClassName("h4_h26");
        H1 h27 = new H1("07950966761"); h27.addClassName("h4_h27");
        VerticalLayout vv2 = new VerticalLayout(); vv2.addClassName("h4_vv2");
        vv2.add(h21, h22, h23, h24, h26, h27);
        dv2.add(vv2);

        Div dv3 = new Div(); dv3.addClassName("h4_dv3");
        H1 h31 = new H1("Account"); h31.addClassName("h4_h31");
        RouterLink h32 = new RouterLink("My Account", HomePage.class); h32.addClassName("h4_h32");
        RouterLink h33 = new RouterLink("Login / Register", HomePage.class); h33.addClassName("h4_h33");
        //RouterLink h34 = new RouterLink("Cart", HomePage.class); h34.addClassName("h4_h34");
        VerticalLayout vv3 = new VerticalLayout(); vv3.addClassName("h4_vv3");
        vv3.add(h31, h32, h33);
        dv3.add(vv3);

        Div dv4 = new Div(); dv4.addClassName("h4_dv4");
        H1 h41 = new H1("Quick Link"); h41.addClassName("h4_h41");
        RouterLink h42 = new RouterLink("Privacy Policy", HomePage.class); h42.addClassName("h4_h42");
        RouterLink h43 = new RouterLink("Terms Of Use", HomePage.class); h43.addClassName("h4_h43");
        RouterLink h44 = new RouterLink("FAQ", HomePage.class); h44.addClassName("h4_h44");
        RouterLink h45 = new RouterLink("Contact Us", HomePage.class); h45.addClassName("h4_h45");
        RouterLink h46 = new RouterLink("Our Services", HomePage.class); h46.addClassName("h4_h45");
        VerticalLayout vv4 = new VerticalLayout(); vv4.addClassName("h4_vv4");
        vv4.add(h41, h42, h43, h44, h45, h46);
        dv4.add(vv4);

        Div dv5 = new Div(); dv5.addClassName("h4_dv5");
        H1 h51 = new H1("Invite a User"); h51.addClassName("h4_h51");
        H1 h52 = new H1("Get a Discount when you Invite a New User"); h52.addClassName("h4_h52");
        Image h53 = new Image("images/qr.png", "QR Code"); h53.addClassName("h4_h53");
        HorizontalLayout h54 = new HorizontalLayout(); h54.addClassName("h4_h54");
        Image h54a = new Image("icons/facebook.svg", "Facebook Link"); h54a.addClassName("h4_h54a");
        Image h54b = new Image("icons/instagram.svg", "Twitter Link"); h54b.addClassName("h4_h54b");
        Image h54c = new Image("icons/twitter.svg", "Instagram Link"); h54c.addClassName("h4_h54c");
        h54.add(h54a, h54b, h54c);
        VerticalLayout vv5 = new VerticalLayout(); vv5.addClassName("h4_vv5");
        vv5.add(h51, h52, h53, h54);
        dv5.add(vv5);

        fl1.setWidthFull();
        fl1.setAlignItems(FlexComponent.Alignment.STRETCH);
        fl1.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        fl1.setAlignContent(FlexLayout.ContentAlignment.CENTER);
        fl1.addClassName("h4_fl");
        fl1.add (dv1, dv2, dv3, dv4, dv5);

        Hr hr = new Hr(); hr.addClassName("h4_hr");

        HorizontalLayout hle = new HorizontalLayout(); hle.addClassName("h4_hle");
        Image hlea = new Image("icons/copyright.svg", "Copyright Icon"); hlea.addClassName("h4_hlea");
        H1 hleb = new H1("Copyright CheckIfScam 2025. All right reserved"); hleb.addClassName("h4_hleb");
        hle.add(hlea, hleb);

        VerticalLayout vl1 = new VerticalLayout();
        vl1.addClassName("h4_vl1");
        vl1.add(fl1);
        vl.add(vl1, hr, hle);
        return vl;
    }
}
