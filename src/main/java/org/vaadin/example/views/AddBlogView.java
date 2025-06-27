package org.vaadin.example.views;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.vaadin.example.entities.Blog;
import org.vaadin.example.entities.Paragraph;
import org.vaadin.example.services.BlogService;
import org.vaadin.example.services.ImageUploadService;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.reflections.Reflections.log;
import com.vaadin.flow.component.UI;

@Route("addBlog")
public class AddBlogView extends VerticalLayout {

    private String errorMessage;
    private final BlogService blogService;
    private final ImageUploadService imageUploadService;

    public AddBlogView(BlogService blogService, ImageUploadService imageUploadService){
        this.blogService = blogService;
        this.imageUploadService = imageUploadService;
        addClassName("addBlogView");
        setWidthFull();
        getStyle().set("align-items", "center");
            //.set("height", "100vh")
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("display", "flex");
        getStyle().set("justify-content", "center");
        VerticalLayout vl = new VerticalLayout();
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.addClassName("bl2_vl");
        vl.setWidth("70vw");
        vl.getStyle().set("padding", "7em")
                .set("gap", "5vh").set("margin-top", "3vh").set("background-color", "white");
        H1 h1 = new H1("Add a New Blog");
        h1.getStyle()
                .set("text-align", "center")
                .set("width", "100%")
                .set("font-size", "2.2em")
                .set("margin-bottom", "4vh");
        TextField title = new TextField("Blog Title");
        title.setWidthFull();
        TextField author = new TextField("Author");
        author.setWidthFull();

        TextArea shortDescription = new TextArea("Short Description");
        shortDescription.setWidthFull();

        MemoryBuffer buffer = new MemoryBuffer();
        Upload uploadImage = new Upload(buffer);
        uploadImage.setDropLabel(new Span("Upload an image for your blog"));
        uploadImage.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif", "image/webp");
        uploadImage.setMaxFiles(1);
        uploadImage.setMaxFileSize(5 * 1024 * 1024);

        TextArea imageString = new TextArea();

        AtomicReference<String> base64Image = new AtomicReference<>("");
        AtomicReference<String> uploadedImageUrl = new AtomicReference<>("");

        uploadImage.addSucceededListener(event -> {
            String filename = event.getFileName();
            InputStream inputStream = buffer.getInputStream();

            try {
                uploadedImageUrl.set(imageUploadService.uploadToCloudinary(inputStream, filename));
                System.out.println("Uploaded Cloudinary Image: " + uploadedImageUrl.get());
                imageString.setValue(uploadedImageUrl.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

//            try {
//                // Convert input stream to byte array
//                byte[] imageBytes = inputStream.readAllBytes();
//
//                // Convert byte array to Base64 string
//                base64Image.set(Base64.getEncoder().encodeToString(imageBytes));
//
//                // Do something with the string
//                System.out.println("Base64 Image String: " + base64Image);
//                imageString.setValue(base64Image.get());
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        });

        uploadImage.addFailedListener(event -> {
            Notification.show("Upload failed: " + event.getReason().getMessage(),
                            5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        uploadImage.addFileRejectedListener(event -> {
            Notification.show("File rejected: " + event.getErrorMessage(),
                            5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        IntegerField parCountField = new IntegerField("Paragraph Count");
        parCountField.setStepButtonsVisible(true);

        HorizontalLayout hl = new HorizontalLayout();
        hl.add(uploadImage, parCountField);
        hl.getStyle().set("justify-content", "space-around");
        hl.setWidthFull();

        Button next = new Button("Next  ->");
        next.getStyle().set("--vaadin-button-height", "5vh").set("cursor", "pointer");
        next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        vl.add(h1, title, author, shortDescription, hl, next);

        next.addClickListener(e -> {

            if (parCountField.isEmpty()){
                Dialog dialog0 = new Dialog();
                dialog0.add(new Text("Fill in paragraph count"));
                Button closeButton0 = new Button("Close");
                closeButton0.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                closeButton0.getStyle().set("cursor", "pointer");
                closeButton0.addClickListener(e4 -> {
                    dialog0.close();
                });
                dialog0.getFooter().add(closeButton0);
                dialog0.open();
            } else {
                Map<Integer, TextArea> parMap = new HashMap<>();

                Binder<Blog> binder = new Binder(Blog.class);

                binder.forField(author).asRequired("Fill in Author")
                        .bind(Blog::getAuthor, Blog::setAuthor);
                binder.forField(title).asRequired("Fill in the Title")
                        .bind(Blog::getTitle, Blog::setTitle);
                binder.forField(shortDescription).asRequired("Enter a Short Description")
                        .bind(Blog::getDescription, Blog::setDescription);

                for (int i = 1; i <= parCountField.getValue(); i++){
                    TextArea textArea = new TextArea("Paragraph " + i);
                    textArea.setWidthFull();
                    binder.forField(textArea).asRequired("Fill in this paragraph");
                    parMap.put(i, textArea);
                    vl.add(textArea);
                }

                Button save = new Button("Publish Blog");
                save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                save.getStyle().set("--vaadin-button-height", "5vh").set("cursor", "pointer");
                save.addClickListener(ee -> {
                    errorMessage = " ";
                    Blog blog = new Blog();
                    List<Paragraph> paragraphList = new ArrayList<>();
                    if (imageString.getValue().equalsIgnoreCase("") || imageString.getValue()== null || imageString.getValue().isEmpty()){
                        errorMessage = errorMessage.concat(" ").concat("You need upload an image" + "\n");
                    }
                    for (Map.Entry<Integer, TextArea> entry : parMap.entrySet()) {
                        int num = entry.getKey();
                        TextArea ta = entry.getValue();
                        Paragraph paragraph = new Paragraph();
                        log.info("Par Textttttt" + ta.getValue());
                        if (ta.getValue().equalsIgnoreCase("") || ta.getValue()== null || ta.getValue().isEmpty()){
                            errorMessage = errorMessage.concat(" ").concat("You need to fill paragraph " + num + "\n");
                        } else {
                            String text = String.valueOf(num) + ta.getValue();
                            paragraph.setText(text);
                            paragraph.setBlog(blog);
                            paragraphList.add(paragraph);
                        }
                    }
                    for (Paragraph par : paragraphList){

                    }
                    if (binder.writeBeanIfValid(blog)) {
                        blog.setTitle(title.getValue());
                        blog.setDescription(shortDescription.getValue());
                        blog.setAuthor(author.getValue());
                        blog.setParagraphs(paragraphList);
                        blog.setCreateDate(LocalDateTime.now());
                        blog.setImageUrl(uploadedImageUrl.get());
                        blogService.saveBlog(blog);
                        Dialog dialog = new Dialog();
                        dialog.setHeaderTitle("Success");
                        dialog.add(new Text("Blog successfully published"));
                        Button closeButton = new Button("Close");
                        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                        closeButton.getStyle().set("cursor", "pointer");
                        closeButton.addClickListener(eee -> {
                            UI.getCurrent().getPage().reload();
                            dialog.close();
                        });
                        dialog.addDialogCloseActionListener(event -> {
                            UI.getCurrent().getPage().reload();
                        });
                        dialog.getFooter().add(closeButton);
                        dialog.open();
                    } else {
                        binder.validate().getValidationErrors().forEach(vr -> {
                            errorMessage = errorMessage.concat(" ").concat(vr.getErrorMessage()).concat("\n");
                        });
                        log.error(errorMessage);
                        String htmlMessage = errorMessage.replace("\n", "<br>");
                        Dialog dialog = new Dialog();
                        dialog.setHeaderTitle("Form Errors");
                        dialog.add(new Html("<div>" + htmlMessage + "</div>"));
                        Button closeButton = new Button("Close");
                        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                        closeButton.getStyle().set("cursor", "pointer");
                        closeButton.addClickListener(eee -> {
                            dialog.close();
                        });
                        dialog.getFooter().add(closeButton);
                        dialog.open();
                    }
                });
                vl.add(save);
                vl.remove(next);
            }
        });

        add(vl);
    }
}
