package com.ecommerce.library.service;

import com.ecommerce.library.model.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);

    Category update(Category category);

    List<Category> findAll();

    Category findById(Long id);

    Category getReferenceById(Long id);

    void deleteById(Long id);

    List<Category> findByActive();

}
