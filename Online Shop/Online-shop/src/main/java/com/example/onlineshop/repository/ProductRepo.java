package com.example.onlineshop.repository;

import com.example.onlineshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_id(Long id);

    List<Product> findAllByCategoryNameAndFlavour(String cat, String flavour);

    List<Product> findByCategoryName(String name);

    List<Product> findAllBySubCategoryName(String name);

    List<Product> findAllByName(String name);

    Product findProductByNameAndFlavourAndWeightAndPresentation(String name, String flavour, Integer weight, String presentation);
}
