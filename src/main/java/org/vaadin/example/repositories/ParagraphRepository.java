package org.vaadin.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.entities.Paragraph;

public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {
}
