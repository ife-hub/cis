package org.vaadin.example.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import org.vaadin.example.entities.Blog;
import org.vaadin.example.entities.Comment;
import org.vaadin.example.entities.PendingComment;
import org.vaadin.example.layouts.MainLayout;
import org.vaadin.example.services.BlogService;
import org.vaadin.example.services.CommentService;
import org.vaadin.example.services.PendingCommentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Route(value = "reviewComments2", layout = MainLayout.class)
public class ReviewComments2 extends VerticalLayout implements HasUrlParameter<String> {

    private final BlogService blogService;
    private List<PendingComment> list = new ArrayList<>();
    private List<PendingComment> list0 = new ArrayList<>();
    private Grid<PendingComment> grid = new Grid<>();
    private ListDataProvider<PendingComment> dataProvider;
    private final PendingCommentService pendingCommentService;
    private final CommentService commentService;

    public ReviewComments2(BlogService blogService,
                           PendingCommentService pendingCommentService,
                           CommentService commentService){
        this.blogService = blogService;
        this.pendingCommentService = pendingCommentService;
        this.commentService = commentService;
    }

//    private void refreshGridData(Blog blog){
//        list0 = pendingCommentService.getAllPendingComments();
//        list.clear();
//        for (PendingComment pc : list0){
//            if (pc.getBlog().getBlogId() == blog.getBlogId()){
//                list.add(pc);
//            }
//        }
//        dataProvider.getItems().clear();
//        dataProvider.getItems().addAll(list);
//        dataProvider.refreshAll();
//    }

    private void refreshGridData(Blog blog) {
        list.clear();
        list.addAll(
                pendingCommentService.getAllPendingComments().stream()
                        .filter(pc -> Objects.equals(pc.getBlog().getBlogId(), blog.getBlogId()))
                        .toList()
        );
        dataProvider.refreshAll();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String blogId){
        removeAll();
        if (blogId != null){
            Blog blog = blogService.getBlogById(Long.parseLong(blogId));
            add(getVl1(blog));
        }
    }

    public VerticalLayout getVl1(Blog blog){
        VerticalLayout vl = new VerticalLayout();
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        list0 = pendingCommentService.getAllPendingComments();
        list.clear();
        for (PendingComment pc : list0){
            if (pc.getBlog().getBlogId() == blog.getBlogId()){
                list.add(pc);
            }
        }

        Div div = new Div(); div.addClassName("ob_div");
        div.getStyle().set("margin-top", "4vh");
        div.getStyle().set("margin-bottom", "4vh");

        Image image = new Image(); image.addClassName("ob_image");
        image.setSrc(blog.getImageUrl());
        image.setAlt(blog.getTitle());

        H1 h1 = new H1(blog.getTitle()); h1.addClassName("ob_h1");
        H1 h2 = new H1(blog.getDescription()); h2.addClassName("ob_h2");

        Div div2 = new Div(); div2.addClassName("ob_div2");

        H1 h3 = new H1(blog.getAuthor() + " "); h3.addClassName("ob_h3");
        LocalDateTime date = blog.getCreateDate();
        String dateString = "-  " +String.valueOf(date.getDayOfMonth()) + " " + String.valueOf(date.getMonth()) + " " + String.valueOf(date.getYear());
        H1 h4 = new H1(dateString); h4.addClassName("ob_h4");

        div2.add(h3, h4);

        div.add(image, h1, h2, div2);

        grid = new Grid<>();
        grid.addColumn(PendingComment::getName).setHeader("User Name").setSortable(true);
        grid.addColumn(PendingComment::getMail).setHeader("Mail").setSortable(true);
        grid.addComponentColumn(this::reviewComment);
        grid.setAllRowsVisible(true);
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.getStyle().set("align-self", "center")
                .set("width", "30vw")
                .set("text-align", "center");

        dataProvider = new ListDataProvider<>(list);
        grid.setDataProvider(dataProvider);

        vl.add(div, grid);

        return vl;
    }

    private Button reviewComment(PendingComment pendingComment){
        Button btn = new Button("Review Comment");
        btn.addClassName("btnTheme");

        btn.addClickListener(e -> {
           Dialog dialog = new Dialog();
           dialog.setHeaderTitle("Review Comment");

            TextField name = new TextField("User Name");
            name.setValue(pendingComment.getName());
            name.setReadOnly(true);

            TextField mail = new TextField("User Mail");
            mail.setValue(pendingComment.getMail());
            mail.setReadOnly(true);

            DatePicker createDate = new DatePicker("Create Date");
            createDate.setValue(pendingComment.getCreateDate().toLocalDate());
            createDate.setReadOnly(true);

            TextArea commentString = new TextArea("Comment");
            commentString.setValue(pendingComment.getCommentString());
            commentString.setWidthFull();
            commentString.setReadOnly(true);

            HorizontalLayout hl = new HorizontalLayout(name, mail, createDate);
            hl.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

            VerticalLayout vl = new VerticalLayout();
            vl.add(hl, commentString);

            dialog.add(vl);

            Button save = new Button("Publish Comment");
            save.addClassName("btnTheme");

            Button close = new Button("Close");
            close.addClassName("clsTheme");
            close.addClickListener(ee -> {
                dialog.close();
            });

            save.addClickListener(ee -> {
                Comment comment = new Comment();
                comment.setBlog(pendingComment.getBlog());
                comment.setCommentString(pendingComment.getCommentString());
                comment.setName(pendingComment.getName());
                comment.setCreateDate(pendingComment.getCreateDate());
                comment.setPublishDate(LocalDateTime.now());
                comment.setMail(pendingComment.getMail());

                commentService.saveComment(comment);
                pendingCommentService.deletePendingComment(pendingComment);
                refreshGridData(pendingComment.getBlog());

                dialog.close();

                Dialog dialog0 = new Dialog();
                dialog0.setHeaderTitle("Success");
                dialog0.add(new Text("Comment published successfully"));
                Button close0 = new Button("Close");
                close0.addClassName("clsTheme");
                close0.addClickListener(eee -> {
                    dialog0.close();
                });
                dialog0.getFooter().add(new HorizontalLayout(close0));
                dialog0.open();
            });

            dialog.getFooter().add(new HorizontalLayout(save, close));

            dialog.open();
        });

        return btn;
    }
}
