package com.workintech.s18challenge.service;

import com.workintech.s18challenge.entity.Author;
import com.workintech.s18challenge.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author findById(long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        }
        throw new RuntimeException("Author with given id is not found:" + id);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }
}
