package org.vaadin.example.services;

import org.springframework.stereotype.Service;
import org.vaadin.example.entities.Blog;
import org.vaadin.example.repositories.BlogRepository;

import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }

    public List<Blog> getAllBlogs(){
        return blogRepository.findAll();
    }

    public Blog getBlogById(Long blogId){
        return blogRepository.findById(blogId).get();
    }

    public Blog saveBlog(Blog blog){
        return blogRepository.save(blog);
    }

    public void deleteBlog(Blog blog){
        blogRepository.delete(blog);
    }
}
