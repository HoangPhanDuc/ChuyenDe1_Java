package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        Category categorySave = new Category(category.getName());
        return categoryRepository.save(categorySave);
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate = categoryRepository.getReferenceById(category.getId());
            categoryUpdate.setName(category.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assert categoryUpdate != null;
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public Category getReferenceById(Long id) {
        return (categoryRepository.getReferenceById(id));
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        categoryRepository.delete(category);
    }

    @Override
    public List<Category> findByActive() {
        return categoryRepository.findAllByActive();
    }
}
