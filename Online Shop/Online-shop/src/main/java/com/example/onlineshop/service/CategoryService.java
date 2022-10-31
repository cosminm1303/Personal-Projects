package com.example.onlineshop.service;


import com.example.onlineshop.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();

    Category saveCategory(Category category);

    Optional<Category> getCategoryById(long id);

    void deleteById(Long id);
}
