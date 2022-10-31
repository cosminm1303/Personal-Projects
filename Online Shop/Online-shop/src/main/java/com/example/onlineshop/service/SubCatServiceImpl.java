package com.example.onlineshop.service;

import com.example.onlineshop.models.SubCategory;
import com.example.onlineshop.repository.SubCatRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubCatServiceImpl implements SubCatService {
    private final SubCatRepo subCatRepo;

    @Override
    public List<SubCategory> getAllSubCategories() {
        return subCatRepo.findAll();
    }

    @Override
    public Optional<SubCategory> getSubCategoryById(long id) {
        return subCatRepo.findById(id);
    }

    @Override
    public SubCategory saveSubCategory(SubCategory subCategory) {
        return subCatRepo.save(subCategory);
    }

    @Override
    public void deleteById(Long id) {
        subCatRepo.deleteById(id);
    }
}
