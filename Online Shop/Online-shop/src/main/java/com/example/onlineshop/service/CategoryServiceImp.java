package com.example.onlineshop.service;


import com.example.onlineshop.models.Category;
import com.example.onlineshop.repository.CatRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CatRepo catRepo;

    @Override
    public List<Category> getAllCategories() {
        return catRepo.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return catRepo.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(long id) {
        return catRepo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        catRepo.deleteById(id);
    }

}
