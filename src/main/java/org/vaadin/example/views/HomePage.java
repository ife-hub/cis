package org.vaadin.example.views;

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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.RouterLink;
import org.vaadin.example.layouts.Layout;

@Route(value="", layout= Layout.class)
public class HomePage extends VerticalLayout {

    public HomePage(){
        UI.getCurrent().getPage().addJavaScript("https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/gsap.min.js");
        UI.getCurrent().getPage().addJavaScript("https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.2/ScrollTrigger.min.js");
        add(getVl1(), getVl4(), getVl3(), getVl2(), getVl5(), getVl6());
        addClassName("vl0");
        setPadding(false);
        setSpacing(false);
    }

    public VerticalLayout getVl1(){
        VerticalLayout vl = new VerticalLayout();
        vl.addClassName("vl");

        H1 h1 = new H1("Check Any URL for Trustworthiness in Seconds");
        h1.addClassName("home_h1");
        h1.setId("heroText");

        UI.getCurrent().getPage().executeJs("""
            gsap.from("#heroText", { opacity: 0, y: 50, duration: 4 });
        """);
        TextField checkURLTF = new TextField();
        checkURLTF.addClassName("home_tf1");
        checkURLTF.setPlaceholder("Enter a URL to check...");
        Button checkCred = new Button("Check Credibility");
        checkCred.addClassName("h_checkCred");
        checkURLTF.setSuffixComponent(checkCred);

        vl.add(h1, checkURLTF);
        return vl;
    }

    public VerticalLayout getVl2(){
        VerticalLayout vl = new VerticalLayout();
        vl.addClassName("h2_vl");
        //vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        Div div = new Div(); div.addClassName("h44_div");
        Div title = new Div(); title.addClassName("h44_title");

        Image vb = new Image("icons/vb.svg", "Vertical Bar");
        vb.addClassName("h_vb");
        vb.setWidth("20px");
        H1 header = new H1("How It Works");
        header.addClassName("h_header");
        title.add(vb, header);
        div.add(title);

        HorizontalLayout hl = new HorizontalLayout();
        hl.addClassName("h2_hl");
        hl.setWidthFull();

        Div div1 = new Div(); div1.addClassName("p2b_div1");
        Div div1a = new Div(); div1a.addClassName("p2b_div1a");
        H1 h1a0 = new H1("1"); h1a0.addClassName("p2b_h1a0");
        div1a.add(h1a0);
        H1 h1a = new H1("Enter a URL"); h1a.addClassName("p2b_h1a");
        div1.add(div1a, h1a);

        Div div2 = new Div(); div2.addClassName("p2b_div2");
        Div div2a = new Div(); div2a.addClassName("p2b_div2a");
        H1 h2a0 = new H1("2"); h2a0.addClassName("p2b_h2a0");
        div2a.add(h2a0);
        H1 h2a = new H1("Our engine checks for the URL's trust score using our data, artificial " +
                "intelligence and public data");
        h2a.addClassName("p2b_h2a");
        div2.add(div2a, h2a);

        Div div3 = new Div(); div3.addClassName("p2b_div3");
        Div div3a = new Div(); div3a.addClassName("p2b_div3a");
        H1 h3a0 = new H1("3"); h3a0.addClassName("p2b_h3a0");
        div3a.add(h3a0);
        H1 h3a = new H1("View detailed credibility report");
        h3a.addClassName("p2b_h3a");
        div3.add(div3a, h3a);

        hl.add(div1, div2, div3);
        vl.add(div, hl);
        return vl;
    }

    public VerticalLayout getVl3(){
        VerticalLayout vl = new VerticalLayout();
        vl.addClassName("h3_vl");

        Div div = new Div();
        div.addClassName("h3_div");

        Div div1 = new Div();
        div1.addClassName("h3_div1");
        H1 h1 = new H1("'It helps me protect my team from phishing links. Highly recommended!'"); h1.addClassName("h3_h1");
        H1 per1 = new H1("Chiamaka A."); per1.addClassName("h3_per1");
        H1 occ1 = new H1("(Student)"); occ1.addClassName("h3_occ1");
        div1.add(h1, per1, occ1);

        Div div2 = new Div();
        div2.addClassName("h3_div2");
        H1 h2 = new H1("'Fast, reliable, and accurate. I use it daily to verify URLs.'");
        h2.addClassName("h3_h2");
        H1 per2 = new H1("Danladi K."); per2.addClassName("h3_per2");
        H1 occ2 = new H1("(Software Developer)"); occ2.addClassName("h3_occ2");
        div2.add(h2, per2, occ2);

        div.add(div1, div2);
        vl.add(div);
        return vl;
    }

    public VerticalLayout getVl4(){
        VerticalLayout vl = new VerticalLayout();
        vl.addClassName("h44_vl");

        Div div = new Div(); div.addClassName("h44_div");

        Div title = new Div(); title.addClassName("h44_title");

        Image vb = new Image("icons/vb.svg", "Vertical Bar");
        vb.addClassName("h_vb");
        vb.setWidth("20px");
        H1 header = new H1("Our Services");
        header.addClassName("h_header");
        title.add(vb, header);
        div.add(title);
        vl.add(div);

        HorizontalLayout hl1 = new HorizontalLayout(); hl1.addClassName("ser_hl1");
        Image img1 = new Image("images/ser1.jpg", "Website Reputation Checker Image"); img1.addClassName("ser_img1");
        VerticalLayout vl1 = new VerticalLayout(); vl1.addClassName("ser_vl1");
        //vl1.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        H1 h11 = new H1("Website Reputation Checker"); h11.addClassName("ser_h11");
        H1 h12 = new H1("Our Website Reputation Checker helps you instantly assess the safety and credibility of any website. It scans URLs against global threat databases to detect phishing, malware, or fraud. Whether you're browsing, buying, or researching, get real-time insights before engaging. Protect yourself and your data with accurate, up-to-date security ratings. Know before you click — every time."); h12.addClassName("ser_h12");
        vl1.add(h11, h12);
        hl1.add(img1, vl1);

        HorizontalLayout hl2 = new HorizontalLayout(); hl2.addClassName("ser_hl2");
        Image img2 = new Image("images/ser2.jpg", "Antivirus Services Image"); img2.addClassName("ser_img2");
        VerticalLayout vl2 = new VerticalLayout(); vl2.addClassName("ser_vl2");
        //vl2.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        H1 h21 = new H1("Antivirus Services"); h21.addClassName("ser_h21");
        H1 h22 = new H1("Stay protected with our powerful Antivirus Services for individuals and businesses. We defend your devices from viruses, ransomware, spyware, and more using real-time and behavioral threat detection. Our lightweight software runs smoothly while keeping your systems secure and updated. Compatible with Windows, macOS, Android, and iOS. Security that adapts as fast as threats evolve"); h22.addClassName("ser_h22");
        vl2.add(h21, h22);
        hl2.add(vl2, img2);

        HorizontalLayout hl3 = new HorizontalLayout(); hl3.addClassName("ser_hl3");
        Image img3 = new Image("images/ser3.jpg", "API Access Image"); img3.addClassName("ser_img3");
        VerticalLayout vl3 = new VerticalLayout(); vl3.addClassName("ser_vl3");
        //vl3.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        H1 h31 = new H1("API Access"); h31.addClassName("ser_h31");
        H1 h32 = new H1("Our API Access lets developers and platforms integrate real-time threat intelligence directly into their apps. Get instant access to URL reputations, domain risk scores, and malware data via a secure, RESTful API. Scalable, fast, and well-documented for easy integration. Ideal for security tools, browser extensions, and corporate platforms. Power your services with trusted cybersecurity data."); h32.addClassName("ser_h32");
        vl3.add(h31, h32);
        hl3.add(img3, vl3);

        vl.add(hl1, hl2, hl3);

        UI.getCurrent().getPage().executeJs("""
    
    // Image hover effects
    const serviceImages = document.querySelectorAll(".ser_img1, .ser_img2, .ser_img3");
    serviceImages.forEach(img => {
        img.addEventListener("mouseenter", () => {
            gsap.to(img, {
                scale: 1.05,
                duration: 0.3,
                ease: "power2.out"
            });
        });
        img.addEventListener("mouseleave", () => {
            gsap.to(img, {
                scale: 1,
                duration: 0.3,
                ease: "power2.out"
            });
        });
    });

""");

        return vl;
    }

    public VerticalLayout getVl5(){
        VerticalLayout vl = new VerticalLayout();
        vl.addClassName("h5_vl");

        H1 h1 = new H1("CheckIfScam");

        Div con = new Div();

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
