package com.workintech.s18challenge.service;

import com.workintech.s18challenge.entity.Author;

public interface AuthorService {
    Author findById(long id);

    Author save(Author author);
}
