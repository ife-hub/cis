package org.vaadin.example.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PendingComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pendingCommentId;

    private String commentString;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime publishDate;
    private String mail;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    public Long getPendingCommentId() {
        return pendingCommentId;
    }

    public void setPendingCommentId(Long pendingCommentId) {
        this.pendingCommentId = pendingCommentId;
    }

    public String getCommentString() {
        return commentString;
    }

    public void setCommentString(String commentString) {
        this.commentString = commentString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
