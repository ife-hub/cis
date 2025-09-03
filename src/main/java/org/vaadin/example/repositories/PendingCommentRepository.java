package org.vaadin.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.entities.PendingComment;

public interface PendingCommentRepository extends JpaRepository<PendingComment, Long> {
}
