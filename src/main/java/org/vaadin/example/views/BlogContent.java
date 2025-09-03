package org.vaadin.example.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.vaadin.example.entities.Blog;
import org.vaadin.example.entities.Comment;
import org.vaadin.example.entities.Paragraph;
import org.vaadin.example.entities.PendingComment;
import org.vaadin.example.layouts.Layout;
import org.vaadin.example.services.BlogService;
import org.vaadin.example.services.CommentService;
import org.vaadin.example.services.ParagraphService;
import org.vaadin.example.services.PendingCommentService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

@Route(value="blogContent", layout= Layout.class)
public class BlogContent extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver, AfterNavigationObserver {

    private final BlogService blogService;
    private final ParagraphService paragraphService;
    private final PendingCommentService pendingCommentService;
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final CommentService commentService;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(EMAIL_REGEX);

    @Override
    public void beforeEnter(BeforeEnterEvent event){
        UI.getCurrent().getPage().executeJs("window.scrollTo(0, 0");
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // Scroll to top after navigation is complete
        UI.getCurrent().getPage().executeJs("window.scrollTo(0, 0);");
    }

    public BlogContent(BlogService blogService, ParagraphService paragraphService,
                       PendingCommentService pendingCommentService,
                       CommentService commentService){
        this.blogService = blogService;
        this.paragraphService = paragraphService;
        this.pendingCommentService = pendingCommentService;
        this.commentService = commentService;
        removeAll();
        addClassName("bl0");
        setPadding(false);
        setSpacing(false);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String blogId){
        removeAll();
        if (blogId != null){
            Blog blog = blogService.getBlogById(Long.parseLong(blogId));
            add(getVl1(blogId), getAddCommentVl(blog), getCommentVl2(blog), getOtherBlogs(blog), getVl6());
        }
    }

    public VerticalLayout getCommentVl2(Blog blog){
        VerticalLayout vl = new VerticalLayout();
        vl.addClassName("bc2_vl");

        H2 h1 = new H2("Comments");
        vl.add(h1);

        List<Comment> li = commentService.getAllComments();
        List<Comment> li2 = new ArrayList<>();

        for (Comment cm : li){
            if (Objects.equals(cm.getBlog().getBlogId(), blog.getBlogId())){
                li2.add(cm);
            }
        }

        for (Comment cm : li2){
            H2 h2 = new H2(cm.getName());
            h2.addClassName("bc2_h2");
            H2 h3 = new H2(formatTimeAgo(cm.getCreateDate()));
            h3.addClassName("bc2_h3");
            com.vaadin.flow.component.html.Paragraph par = new com.vaadin.flow.component.html.Paragraph(cm.getCommentString());
            par.getStyle().set("width", "50vw");

            HorizontalLayout hl = new HorizontalLayout(h2, h3);
            hl.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

            VerticalLayout vl0 = new VerticalLayout();
            vl0.add(hl, par);
            vl.add(vl0);
        }

        return vl;
    }

    public String formatTimeAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();

        if (dateTime.isAfter(now)) {
            return "in the future"; // handle future values if needed
        }

        long years = ChronoUnit.YEARS.between(dateTime, now);
        if (years > 0) return years + (years == 1 ? " year ago" : " years ago");

        long months = ChronoUnit.MONTHS.between(dateTime, now);
        if (months > 0) return months + (months == 1 ? " month ago" : " months ago");

        long weeks = ChronoUnit.WEEKS.between(dateTime, now);
        if (weeks > 0) return weeks + (weeks == 1 ? " week ago" : " weeks ago");

        long days = ChronoUnit.DAYS.between(dateTime, now);
        if (days > 0) return days + (days == 1 ? " day ago" : " days ago");

        long hours = ChronoUnit.HOURS.between(dateTime, now);
        if (hours > 0) return hours + (hours == 1 ? " hour ago" : " hours ago");

        long minutes = ChronoUnit.MINUTES.between(dateTime, now);
        if (minutes > 0) return minutes + (minutes == 1 ? " minute ago" : " minutes ago");

        return "just now";
    }

    public VerticalLayout getVl1(String blogId){
        VerticalLayout vl = new VerticalLayout();
        vl.addClassName("bc_vl1");
        vl.setWidthFull();

        Long value = Long.parseLong(blogId);
        Blog blog = blogService.getBlogById(value);

        H1 h1 = new H1(blog.getTitle()); h1.addClassName("bc_vl1_h1");

        String authorString = "by " + blog.getAuthor();
        H1 h2 = new H1(authorString); h2.addClassName("bc_vl1_h2");

        String imgUrl = blog.getImageUrl();

        String largeUrl = imgUrl.replace("/upload/", "/upload/w_1920,h_1080,c_fill,q_auto/");

        Div firDiv = new Div();
        firDiv.addClassName("firDiv");
        String firBg = "rgba(0, 0, 0, 0.7) url('" + largeUrl + "')";
        firDiv.getStyle().set("background", firBg);

        List<Paragraph> pars = paragraphService.getAllByBlogId(blog);

        vl.add(h1, h2, firDiv);

        VerticalLayout parVl = new VerticalLayout();
        parVl.setPadding(false);
        parVl.setSpacing(false);
        parVl.addClassName("bc_parVl");

        for (Paragraph pp : pars){
            String originalText = pp.getText();
            String modifiedText = originalText.substring(1);
            com.vaadin.flow.component.html.Paragraph par = new com.vaadin.flow.component.html.Paragraph(modifiedText);
            par.addClassName("bc_par");
            parVl.add(par);
        }

        vl.add(parVl);

        return vl;
    }

    public VerticalLayout getAddCommentVl(Blog blog){
        VerticalLayout vl = new VerticalLayout();
        vl.addClassName("bc_gac_vl");
        //vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        //H1 h1 = new H1("Add a Comment"); h1.addClassName("bc_gac_h1");
        TextArea cmt = new TextArea("Add a Comment"); cmt.addClassName("bc_gac_cmt");
        TextField name = new TextField("Name"); name.addClassName("bc_gac_name");
        TextField email = new TextField("Email"); email.addClassName("bc_gac_email");
        Button submit = new Button("Submit"); submit.addClassName("bc_gac_submit");

        submit.addClickListener(e -> {
            if (cmt.getValue().trim().isEmpty()){
                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Error");
                dialog.add(new Text("Comment cannot be empty"));
                Button close = new Button("Close");
                close.addClassName("clsTheme");
                close.addClickListener(ee -> {
                    dialog.close();
                });
                dialog.getFooter().add(close);
                dialog.open();
            }
            else if (name.getValue().trim().isEmpty()){
                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Error");
                dialog.add(new Text("Name cannot be empty"));
                Button close = new Button("Close");
                close.addClassName("clsTheme");
                close.addClickListener(ee -> {
                    dialog.close();
                });
                dialog.getFooter().add(close);
                dialog.open();
            }
            else if (email.getValue().trim().isEmpty()){
                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Error");
                dialog.add(new Text("Mail cannot be empty"));
                Button close = new Button("Close");
                close.addClassName("clsTheme");
                close.addClickListener(ee -> {
                    dialog.close();
                });
                dialog.getFooter().add(close);
                dialog.open();
            }
            else if (!isValidEmail(email.getValue())){
                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Error");
                dialog.add(new Text("Invalid email"));
                Button close = new Button("Close");
                close.addClassName("clsTheme");
                close.addClickListener(ee -> {
                    dialog.close();
                });
                dialog.getFooter().add(close);
                dialog.open();
            }
            else {
                PendingComment pendingComment = new PendingComment();
                pendingComment.setCommentString(cmt.getValue());
                pendingComment.setName(name.getValue());
                pendingComment.setCreateDate(LocalDateTime.now());
                pendingComment.setPublishDate(null);
                pendingComment.setBlog(blog);
                pendingComment.setMail(email.getValue());
                pendingCommentService.savePendingComment(pendingComment);
                cmt.clear();
                name.clear();
                email.clear();

                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Success");
                dialog.add(new Text("Comment has been saved"));
                Button close = new Button("Close");
                close.addClassName("clsTheme");
                close.addClickListener(ee -> {
                    dialog.close();
                });
                dialog.getFooter().add(close);
                dialog.open();
            }
        });

        HorizontalLayout hl = new HorizontalLayout(name, email); hl.addClassName("bc_gac_hl");

        vl.add(cmt, hl, submit);

        return vl;
    }

    public VerticalLayout getOtherBlogs(Blog blog){
        VerticalLayout vl = new VerticalLayout(); vl.addClassName("ob_vl");

        H1 h00 = new H1("Other Blog Posts"); h00.addClassName("bl_h4");
        vl.add(h00);

        List<Blog> allBlogs = blogService.getAllBlogs();
        allBlogs.remove(blog);

        List<Blog> otherBlogs = new ArrayList<>();

        int j = 0;

        for(int i=0; i<allBlogs.size(); i++){
            Blog blogItem = allBlogs.get(i);

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

                    AtomicReference<Blog> blogRef = new AtomicReference<>();
                    blogRef.set(bl);

                    div.addClickListener(e -> {
                        UI.getCurrent().navigate("blogContent/" + blogRef.get().getBlogId());
                        UI.getCurrent().getPage().executeJs("window.scrollTo(0, 0);");
                    });

                    hl.add(div);
                    j++;
                }
                vl.add(hl);
                otherBlogs.clear();
            }
        }

        Button viewMoreBlogs = new Button("View More Blogs");
        viewMoreBlogs.addClassName("bl_viewMoreBlogs");
        viewMoreBlogs.addClickListener(e -> {
            UI.getCurrent().navigate(AllBlogsView.class);
        });

        vl.add(viewMoreBlogs);

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
        Image icon1 = new Image("https://res.cloudinary.com/drtlnc2tx/image/upload/v1756911121/send_lxmbz7.svg", "Send Icon");
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
        Image h53 = new Image("https://res.cloudinary.com/drtlnc2tx/image/upload/v1756911137/qr_hbywi8.png", "QR Code"); h53.addClassName("h4_h53");
        HorizontalLayout h54 = new HorizontalLayout(); h54.addClassName("h4_h54");
        Image h54a = new Image("https://res.cloudinary.com/drtlnc2tx/image/upload/v1756911120/facebook_fbikd7.svg", "Facebook Link"); h54a.addClassName("h4_h54a"); h54a.getStyle().set("width", "24px").set("height", "24px");
        Image h54b = new Image("https://res.cloudinary.com/drtlnc2tx/image/upload/v1756911121/instagram_n3segj.svg", "Instagram Link"); h54b.addClassName("h4_h54b"); h54b.getStyle().set("width", "24px").set("height", "24px");
        Image h54c = new Image("https://res.cloudinary.com/drtlnc2tx/image/upload/v1756911350/icons8-x_exh0ji.svg", "X Link"); h54c.addClassName("h4_h54c"); h54c.getStyle().set("width", "24px").set("height", "24px");
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
        Image hlea = new Image("https://res.cloudinary.com/drtlnc2tx/image/upload/v1756911120/copyright_mhmwfs.svg", "Copyright Icon"); hlea.addClassName("h4_hlea");
        H1 hleb = new H1("Copyright CheckIfScam 2025. All right reserved"); hleb.addClassName("h4_hleb");
        hle.add(hlea, hleb);

        VerticalLayout vl1 = new VerticalLayout();
        vl1.addClassName("h4_vl1");
        vl1.add(fl1);
        vl.add(vl1, hr, hle);
        return vl;
    }
}
