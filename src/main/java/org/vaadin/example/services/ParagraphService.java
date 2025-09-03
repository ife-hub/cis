package org.vaadin.example.services;

import org.springframework.stereotype.Service;
import org.vaadin.example.entities.Blog;
import org.vaadin.example.entities.Paragraph;
import org.vaadin.example.repositories.ParagraphRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParagraphService {

    private final ParagraphRepository paragraphRepository;

    public ParagraphService(ParagraphRepository paragraphRepository){
        this.paragraphRepository = paragraphRepository;
    }

    public List<Paragraph> getAllByBlogId(Blog blog){
        List<Paragraph> pars = paragraphRepository.findAll();
        List<Paragraph> pars2 = new ArrayList<>();

        for (Paragraph par : pars){
            if (par.getBlog().getBlogId() == blog.getBlogId()){
                pars2.add(par);
            }
        }

        return pars2;
    }
}
