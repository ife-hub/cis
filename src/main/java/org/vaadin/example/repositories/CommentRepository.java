package org.vaadin.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
