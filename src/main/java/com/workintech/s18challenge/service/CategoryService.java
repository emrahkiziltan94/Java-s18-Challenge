package com.workintech.s18challenge.service;

import com.workintech.s18challenge.entity.Category;

public interface CategoryService {
    Category findById(long id);

    Category save(Category category);


}
