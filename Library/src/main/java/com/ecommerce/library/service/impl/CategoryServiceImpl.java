package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            categoryUpdate = categoryRepository.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_active(category.is_active());
            categoryUpdate.set_deleted(category.is_deleted());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public Category getReferenceById(Long id) {
        return(categoryRepository.getReferenceById(id));
    }

    @Override
    public Category findById(Long id) {

        System.out.println(categoryRepository.findById(id).get());
        return categoryRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        category.set_active(false);
        category.set_deleted(true);
        categoryRepository.save(category);
    }

    @Override
    public void enableById(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        category.set_active(true);
        category.set_deleted(false);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findByActive() {
        return categoryRepository.findAllByActive();
    }

    @Override
    public List<CategoryDto> getCategoryAndProducts() {
        return categoryRepository.getCategoryAndProduct();
    }
}
