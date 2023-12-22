package com.workintech.s18challenge.repository;

import com.workintech.s18challenge.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
