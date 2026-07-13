package com.example.databasework.service;

import com.example.databasework.entity.AuthorEntity;
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
        AuthorEntity author1 = new AuthorEntity();
        author1.setAuthor("Bogdan");

        AuthorEntity author2 = new AuthorEntity();
        author2.setAuthor("David");

        authorRepository.save(author1);
        authorRepository.save(author2);
    }

}
