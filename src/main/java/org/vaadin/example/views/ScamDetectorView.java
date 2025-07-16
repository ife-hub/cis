package org.vaadin.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.vaadin.example.layouts.Layout;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Route(value="website-reputation-checker", layout= Layout.class)
@Slf4j
public class ScamDetectorView extends VerticalLayout implements HasUrlParameter<String> {

    HashMap<String, String> mediaString = new HashMap<>();
    private static final String DOMAIN_REGEX =
            "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null) {
            addClassName("scamDetectorView");
            try {
                add(getVl(parameter), getVl6());
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            setPadding(false);
            setSpacing(false);
        } else {
            addClassName("scamDetectorView");
            try {
                add(getVl(parameter), getVl6());
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            setPadding(false);
            setSpacing(false);
        }
    }

//    public ScamDetectorView() throws UnirestException, JSONException {
//        addClassName("scamDetectorView");
//        add(getVl(), getVl6());
//        setPadding(false);
//        setSpacing(false);
//    }

    public VerticalLayout getVl(String parameter) throws UnirestException, JSONException {
        VerticalLayout vl = new VerticalLayout();
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        TextField checkURLTF = new TextField();
        checkURLTF.addClassName("home_tf1");
        checkURLTF.setPlaceholder("Enter a URL to check...");
        Button checkCred = new Button("Check Credibility");
        checkCred.addClassName("h_checkCred");
        checkURLTF.setSuffixComponent(checkCred);

        TextField search = new TextField("Search by domain:");
        Button searchButton = new Button("Search");
        searchButton.getStyle().set("align-self", "end");
        searchButton.getStyle().set("cursor", "pointer");
        String searchString = search.getValue();

        HorizontalLayout hl1 = new HorizontalLayout();
        hl1.setVerticalComponentAlignment(Alignment.BASELINE);
        hl1.add(search, searchButton);


        //vl.add(hl1);
        vl.add(checkURLTF);
        //searchButton.addClickListener(e -> {
        checkCred.addClickListener(e -> {
            vl.removeAll();
            //String domain = extractDomain(search.getValue());
            String domain = extractDomain(checkURLTF.getValue());
            log.info("DDD" + domain);
            TextField checkURLTF2 = new TextField();
            checkURLTF2.addClassName("home_tf1");
            checkURLTF2.setPlaceholder("Enter a URL to check...");
            checkURLTF2.setValue(checkURLTF.getValue());
            Button checkCred2 = new Button("Check Credibility");
            checkCred2.addClassName("h_checkCred");
            checkURLTF2.setSuffixComponent(checkCred2);
            vl.add(checkURLTF2);
            if (domain.equalsIgnoreCase("invalid")){
                Div div = new Div();
                div.addClassName("invDiv");
                H1 h1w = new H1("Invalid Domain URL");
                div.add(h1w);
                vl.add(div);
            } else {
                VerticalLayout hl2 = null;
                try {
                    hl2 = getHl2(checkURLTF.getValue());
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                } catch (UnirestException ex) {
                    throw new RuntimeException(ex);
                }
                vl.add(hl2);
            }
        });

        if(parameter != null){
            checkURLTF.setValue(parameter);
            checkCred.click();
        }


        return vl;
    }

    public VerticalLayout getMediaDiv(HashMap<String, String> strs){
        VerticalLayout vl = new VerticalLayout();
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("margin-top", "4vh");

        H1 h1 = new H1("Social Media Links");
        h1.addClassName("sH1");

        HorizontalLayout mediaHl = new HorizontalLayout();
        mediaHl.addClassName("mediaHl");

        for (Map.Entry<String, String> str1 : strs.entrySet()) {
            String key = str1.getKey();
            String value = str1.getValue();

            if (key.equalsIgnoreCase("twitter")){
                Image twitterImage = new Image("icons/x.svg", "X Link");
                twitterImage.setWidth("50px");
                twitterImage.setHeight("50px");
                Anchor twitterLink = new Anchor(value, twitterImage);
                twitterLink.getStyle().set("cursor", "pointer");
                twitterLink.setTarget("_blank");
                mediaHl.add(twitterLink);
            } else if (key.equalsIgnoreCase("facebook")){
                Image facebookImage = new Image("icons/facebook2.svg", "Facebook Link");
                facebookImage.setWidth("50px");
                facebookImage.setHeight("50px");
                Anchor facebookLink = new Anchor(value, facebookImage);
                facebookLink.getStyle().set("cursor", "pointer");
                facebookLink.setTarget("_blank");
                mediaHl.add(facebookLink);
            } else if (key.equalsIgnoreCase("linkedin")){
                Image linkedinImage = new Image("icons/linkedin.svg", "LinkedIn Link");
                linkedinImage.setWidth("50px");
                linkedinImage.setHeight("50px");
                Anchor linkedinLink = new Anchor(value, linkedinImage);
                linkedinLink.getStyle().set("cursor", "pointer");
                linkedinLink.setTarget("_blank");
                mediaHl.add(linkedinLink);
            } else if (key.equalsIgnoreCase("instagram")){
                Image instaImage = new Image("icons/instagram3.svg", "Instagram Link");
                instaImage.setWidth("50px");
                instaImage.setHeight("50px");
                Anchor instaLink = new Anchor(value, instaImage);
                instaLink.getStyle().set("cursor", "pointer");
                instaLink.setTarget("_blank");
                mediaHl.add(instaLink);
            } else if (key.equalsIgnoreCase("youtube")){
                Image youtubeImage = new Image("icons/youtube.svg", "Youtube Link");
                youtubeImage.setWidth("50px");
                youtubeImage.setHeight("50px");
                Anchor youtubeLink = new Anchor(value, youtubeImage);
                youtubeLink.getStyle().set("cursor", "pointer");
                youtubeLink.setTarget("_blank");
                mediaHl.add(youtubeLink);
            }
        }

        vl.add(h1, mediaHl);
        return vl;
    }

    public VerticalLayout getHl2(String searchString) throws JSONException, UnirestException {
        VerticalLayout vl = new VerticalLayout();
        JSONObject jsonObject = new JSONObject(getDomainScorecard(searchString));

        String s1 = jsonObject.getString("name");
        String s2 = jsonObject.getString("domain");
        String s3 = jsonObject.getString("logo");
        String s4 = jsonObject.getString("safetyStatus");
        String s5 = jsonObject.getString("safetyReputation");
        String s6 = jsonObject.getString("childSafetyReputation");
        String s7 = jsonObject.getString("organization");
        String s8 = jsonObject.getString("description");
        String s9 = jsonObject.getString("industry");
        String s10 = jsonObject.getString("twitter");
        String s11 = jsonObject.getString("facebook");
        String s12 = jsonObject.getString("linkedin");
        String s13 = jsonObject.getString("instagram");
        String s14 = jsonObject.getString("youtube");
        String s15 = jsonObject.getString("company_type");


        HorizontalLayout hl2 = new HorizontalLayout();
        hl2.setVerticalComponentAlignment(Alignment.CENTER);
        hl2.addClassName("hl2");

        Div leftDiv = new Div();
        Image orgPic = new Image(s3, "Agricyclers Logo");
        leftDiv.add(orgPic);
        leftDiv.addClassName("leftDiv");
        orgPic.getStyle().set("margin-left", "5vw")
                .set("margin-right", "5vw");

        Div rightDiv = new Div();
        rightDiv.addClassName("rightDiv");

        VerticalLayout vl2 = new VerticalLayout();

        H1 h1 = new H1("Name: ");
        H1 h2 = new H1("Domain: ");
        H1 h4 = new H1("Safety Status: ");
        H1 h5 = new H1("Safety Rating: ");
        H1 h6 = new H1("Child Safety Rating: ");
        H1 h7 = new H1("Organization: ");
        H1 h8 = new H1("Description: ");
        H1 h9 = new H1("Industry: ");
        H1 h15 = new H1("Company Type: ");

//        s7 = "sorvobdvn dobdidd";
//        s9 = "csbosnsirr sbofb dnvov  dbodbod";
//        s8 = "Loremvno dipdonbdiod dibdbpdnd idvpdpnvd divpndpnd ddpvnndv dondpndodv dovbdvndpnd dodpndvpdv";

        H1 h1b = new H1(s1); h1b.getStyle().set("align-self", "start");
        H1 h2b = new H1(s2); h2b.getStyle().set("align-self", "start");
        H1 h4b = new H1(s4); h4b.getStyle().set("align-self", "start");
        H1 h5b = new H1(s5); h5b.getStyle().set("align-self", "start");
        H1 h6b = new H1(s6); h6b.getStyle().set("align-self", "start");
        H1 h7b = new H1(s7); h7b.getStyle().set("align-self", "start");
        H1 h8b = new H1(s8); h8b.getStyle().set("align-self", "start");
        H1 h9b = new H1(s9); h9b.getStyle().set("align-self", "start");
        H1 h15b = new H1(s15); h15b.getStyle().set("align-self", "start");

//        H1 h10 = new H1("Twitter:   ");
//        H1 h11 = new H1("Facebook:   ");
//        H1 h12 = new H1("LinkedIn:   ");
//        H1 h13 = new H1("Instagram:   " + s13);
//        H1 h14 = new H1("Youtube: " + s14);

        VerticalLayout vlR1 = new VerticalLayout(h1, h2, h4, h5, h6, h7, h8, h9, h15);
        vlR1.setDefaultHorizontalComponentAlignment(Alignment.END);
        vlR1.setWidth("35vw");
        VerticalLayout vlR2 = new VerticalLayout(h1b, h2b, h4b, h5b, h6b, h7b, h8b, h9b, h15b);
        vlR2.setDefaultHorizontalComponentAlignment(Alignment.START);

        HorizontalLayout hy1b = new HorizontalLayout(h1); hy1b.addClassName("hy1b"); HorizontalLayout hy1c = new HorizontalLayout(h1b); hy1c.addClassName("hy1c");
        HorizontalLayout hy1 = new HorizontalLayout(hy1b, hy1c); hy1.addClassName("hy1");
        HorizontalLayout hy2b = new HorizontalLayout(h2); hy2b.addClassName("hy1b"); HorizontalLayout hy2c = new HorizontalLayout(h2b); hy2c.addClassName("hy1c");
        HorizontalLayout hy2 = new HorizontalLayout(hy2b, hy2c); hy2.addClassName("hy1");
        HorizontalLayout hy4b = new HorizontalLayout(h4); hy4b.addClassName("hy1b"); HorizontalLayout hy4c = new HorizontalLayout(h4b); hy4c.addClassName("hy1c");
        HorizontalLayout hy4 = new HorizontalLayout(hy4b, hy4c); hy4.addClassName("hy1");
        HorizontalLayout hy5b = new HorizontalLayout(h5); hy5b.addClassName("hy1b"); HorizontalLayout hy5c = new HorizontalLayout(h5b); hy5c.addClassName("hy1c");
        HorizontalLayout hy5 = new HorizontalLayout(hy5b, hy5c); hy5.addClassName("hy1");
        HorizontalLayout hy6b = new HorizontalLayout(h6); hy6b.addClassName("hy1b"); HorizontalLayout hy6c = new HorizontalLayout(h6b); hy6c.addClassName("hy1c");
        HorizontalLayout hy6 = new HorizontalLayout(hy6b, hy6c); hy6.addClassName("hy1");
        HorizontalLayout hy7b = new HorizontalLayout(h7); hy7b.addClassName("hy1b"); HorizontalLayout hy7c = new HorizontalLayout(h7b); hy7c.addClassName("hy1c");
        HorizontalLayout hy7 = new HorizontalLayout(hy7b, hy7c); hy7.addClassName("hy1");
        HorizontalLayout hy8b = new HorizontalLayout(h8); hy8b.addClassName("hy1b"); HorizontalLayout hy8c = new HorizontalLayout(h8b); hy8c.addClassName("hy1c");
        HorizontalLayout hy8 = new HorizontalLayout(hy8b, hy8c); hy8.addClassName("hy1");
        HorizontalLayout hy9b = new HorizontalLayout(h9); hy9b.addClassName("hy1b"); HorizontalLayout hy9c = new HorizontalLayout(h9b); hy9c.addClassName("hy1c");
        HorizontalLayout hy9 = new HorizontalLayout(hy9b, hy9c); hy9.addClassName("hy1");
        HorizontalLayout hy15b = new HorizontalLayout(h15); hy15b.addClassName("hy1b"); HorizontalLayout hy15c = new HorizontalLayout(h15b); hy15c.addClassName("hy1c");
        HorizontalLayout hy15 = new HorizontalLayout(hy15b, hy15c); hy15.addClassName("hy1");

        HorizontalLayout hlR = new HorizontalLayout(vlR1, vlR2);

        //s13 = "https://www.instagram.com/";

        if (!s10.equalsIgnoreCase("")){mediaString.put("twitter", s10);}
        if (!s11.equalsIgnoreCase("")){mediaString.put("facebook", s11);}
        if (!s12.equalsIgnoreCase("")){mediaString.put("linkedin", s12);}
        if (!s13.equalsIgnoreCase("")){mediaString.put("instagram", s13);}
        if (!s14.equalsIgnoreCase("")){mediaString.put("youtube", s14);}
        //vl2.add(h1, h2, h4, h5, h6, h7, h8, h9, h10, h11, h12, h13, h14, h15);

        //vl2.add(hlR);
        vl2.add(hy1, hy2, hy4, hy5, hy6, hy7, hy8, hy8, hy15);
        rightDiv.add(vl2);

        hl2.add(leftDiv, rightDiv);

        vl.add(hl2);

        //log.info("MMM", mediaString.toString());
        if (!mediaString.isEmpty()){
            vl.add(getMediaDiv(mediaString));
        }

        return vl;
    }

    public String getDomainScorecard(String domain) throws UnirestException, JSONException {
        String url = "https://scorecard.api.mywot.com/v3/targets?t=" + domain;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-user-id", "9102335");
        headers.set("x-api-key", "baa61758eb742d536746886f8e90a0e324542609");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> wotResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        String url2 = "https://autocomplete.clearbit.com/v1/companies/suggest?query=" + domain;
        ResponseEntity<String> clearBitResponse = restTemplate.getForEntity(url2, String.class);

        String url3 = "https://api.hunter.io/v2/domain-search?domain=" + domain +
                "&api_key=85bb5e1b441f3d0993495d6bb8e0ebfe52d5b479";
        ResponseEntity<String> hunterResponse = restTemplate.getForEntity(url3, String.class);

        log.info(String.valueOf(clearBitResponse.getBody()));
        JSONArray wotArray = new JSONArray(wotResponse.getBody());
        JSONObject wotObject = wotArray.getJSONObject(0);
        JSONObject safetyObject = new JSONObject();
        JSONObject childSafetyObject = new JSONObject();
        String target = ""; String safetyStatus = ""; String safetyReputation = "";
        String safetyConfidence = ""; String childSafetyReputation=""; String childSafetyConfidence = "";
        if (wotObject.has("safety"))
        {
            safetyObject = wotObject.getJSONObject("safety");
            if (safetyObject.has("status")){safetyStatus=safetyObject.getString("status");}
            if (safetyObject.has("reputations")){safetyReputation=safetyObject.getString("reputations");}
            if (safetyObject.has("confidence")){safetyConfidence=safetyObject.getString("confidence");}
        }
        if (wotObject.has("childSafety")){
            childSafetyObject = wotObject.getJSONObject("childSafety");
            if (childSafetyObject.has("reputations")){childSafetyReputation=childSafetyObject.getString("reputations");}
            if (childSafetyObject.has("confidence")){childSafetyConfidence=childSafetyObject.getString("confidence");}
        }
        if (wotObject.has("target")){
            target = wotObject.getString("target");
        }


        JSONArray cArray = new JSONArray(clearBitResponse.getBody());
        String domainString = ""; String logo="";
        JSONObject cObject = cArray.getJSONObject(0);
        if (cObject.has("domain")){domainString=cObject.getString("domain");}
        if (cObject.has("logo")){logo=cObject.getString("logo");}

        JSONObject hObject = new JSONObject(hunterResponse.getBody());
        JSONObject hObject2 = new JSONObject();
        String organization = ""; String description = ""; String industry="";
        String twitter=""; String facebook="";
        String linkedin=""; String instagram=""; String youtube="";
        String company_type = "";
        if (hObject.has("data"))
        {
            hObject2 = hObject.getJSONObject("data");
            if (hObject2.has("organization")){organization=hObject2.getString("organization");}
            if (hObject2.has("description")){description=hObject2.getString("description");}
            if (hObject2.has("industry")){industry=hObject2.getString("industry");}
            if (hObject2.has("twitter")){twitter=hObject2.getString("twitter");}
            if (hObject2.has("facebook")){facebook=hObject2.getString("facebook");}
            if (hObject2.has("linkedin")){linkedin=hObject2.getString("linkedin");}
            if (hObject2.has("instagram")){instagram=hObject2.getString("instagram");}
            if (hObject2.has("youtube")){youtube=hObject2.getString("youtube");}
            if (hObject2.has("company_type")){company_type=hObject2.getString("company_type");}
        }

        JSONObject responseObject = new JSONObject();
        responseObject.put("name", target);
        responseObject.put("domain", domainString);
        responseObject.put("logo", logo);
        responseObject.put("safetyStatus", safetyStatus);
        responseObject.put("safetyReputation", safetyReputation);
        responseObject.put("safetyConfidence", safetyConfidence);
        responseObject.put("childSafetyReputation", childSafetyReputation);
        responseObject.put("childSafetyConfidence", childSafetyConfidence);
        responseObject.put("organization", organization);
        responseObject.put("description", description);
        responseObject.put("industry", industry);
        responseObject.put("twitter", twitter);
        responseObject.put("facebook", facebook);
        responseObject.put("linkedin", linkedin);
        responseObject.put("instagram", instagram);
        responseObject.put("youtube", youtube);
        responseObject.put("company_type", company_type);

        return responseObject.toString();
    }

    public static String extractDomain(String url) {
        if (url == null || url.trim().isEmpty()) {
            return "invalid";
        }

        try {
            // Add scheme if missing
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }

            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();

            if (host == null || host.trim().isEmpty()) {
                return "invalid";
            }

            // Clean up domain
            if (host.endsWith(".")) {
                host = host.substring(0, host.length() - 1);
            }

            if (host.startsWith("www.")) {
                host = host.substring(4);
            }

            // Validate the domain structure


            try {
                if (Pattern.matches(DOMAIN_REGEX, host)) {
                    InetAddress.getByName(host); // Throws UnknownHostException if DNS fails
                    return host;
                } else {
                    return "invalid";
                }
            } catch (UnknownHostException e) {
                return "invalid";
            }

            //return host;
        } catch (MalformedURLException e) {
            return "invalid";
        }
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
