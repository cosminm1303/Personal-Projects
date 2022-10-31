package com.example.onlineshop.service;

import com.example.onlineshop.models.Category;
import com.example.onlineshop.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Product saveProduct(Product product);

    Category getCategory(Product product);

    void deleteById(Long id);

    Optional<Product> getProductById(Long id);

    List<Product> findByCategory_id(Long id);

    List<Product> findBySubCategoryName(String name);

    List<Product> findByName(String name);
}
