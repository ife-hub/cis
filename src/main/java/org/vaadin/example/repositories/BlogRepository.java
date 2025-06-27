package org.vaadin.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.entities.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
