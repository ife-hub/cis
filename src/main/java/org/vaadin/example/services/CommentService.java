package org.vaadin.example.services;

import org.springframework.stereotype.Service;
import org.vaadin.example.entities.Comment;
import org.vaadin.example.repositories.CommentRepository;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long commentId){
        return commentRepository.findById(commentId).get();
    }

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }
}
