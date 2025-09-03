package org.vaadin.example.services;

import org.springframework.stereotype.Service;
import org.vaadin.example.entities.Comment;
import org.vaadin.example.entities.PendingComment;
import org.vaadin.example.repositories.PendingCommentRepository;

import java.util.List;

@Service
public class PendingCommentService {

    private final PendingCommentRepository pendingCommentRepository;

    public PendingCommentService(PendingCommentRepository pendingCommentRepository){
        this.pendingCommentRepository = pendingCommentRepository;
    }

    public List<PendingComment> getAllPendingComments(){
        return pendingCommentRepository.findAll();
    }

    public PendingComment getPendingCommentById(Long pendingCommentId){
        return pendingCommentRepository.findById(pendingCommentId).get();
    }

    public PendingComment savePendingComment(PendingComment pendingComment){
        return pendingCommentRepository.save(pendingComment);
    }

    public void deletePendingComment(PendingComment pendingComment){
        pendingCommentRepository.delete(pendingComment);
    }
}
