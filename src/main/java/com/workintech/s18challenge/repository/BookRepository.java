package com.workintech.s18challenge.repository;

import com.workintech.s18challenge.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
