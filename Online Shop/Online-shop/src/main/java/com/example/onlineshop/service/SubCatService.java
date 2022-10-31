package com.example.onlineshop.service;


import com.example.onlineshop.models.SubCategory;

import java.util.List;
import java.util.Optional;

public interface SubCatService {
    List<SubCategory> getAllSubCategories();

    SubCategory saveSubCategory(SubCategory subCategory);

    Optional<SubCategory> getSubCategoryById(long id);

    void deleteById(Long id);
}
