package com.workintech.s18challenge.service;

import com.workintech.s18challenge.entity.Book;

public interface BookService {
    Book findById(long id);
    Book save(Book book);
}
