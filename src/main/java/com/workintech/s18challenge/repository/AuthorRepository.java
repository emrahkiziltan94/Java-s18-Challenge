package com.workintech.s18challenge.repository;

import com.workintech.s18challenge.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
