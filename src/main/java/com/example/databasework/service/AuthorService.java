package com.example.databasework.service;

import com.example.databasework.entity.Authors;
import com.example.databasework.repository.AuthorRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadauthors() {
        Authors author1 = new Authors();
        author1.setAuthor("Bogdan");

        Authors author2 = new Authors();
        author2.setAuthor("David");

        authorRepository.save(author1);
        authorRepository.save(author2);
    }

}
