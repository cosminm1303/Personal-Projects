package com.example.onlineshop.service;

import com.example.onlineshop.models.Category;
import com.example.onlineshop.models.Product;
import com.example.onlineshop.repository.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Category getCategory(Product product) {
        return product.getCategory();
    }

    @Override
    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public List<Product> findByCategory_id(Long id) {
        return productRepo.findAllByCategory_id(id);
    }


    @Override
    public List<Product> findBySubCategoryName(String name) {
        return productRepo.findAllBySubCategoryName(name);
    }

    public List<Product> findByCategoryName(String cat) {
        return productRepo.findByCategoryName(cat);
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepo.findAllByName(name);
    }

    public Product findByFWP(String name, String flavour, Integer weight, String presentation) {
        return productRepo.findProductByNameAndFlavourAndWeightAndPresentation(name, flavour, weight, presentation);
    }

    public List<Product> findByCatAndFlavour(String cat, String flavour) {
        return productRepo.findAllByCategoryNameAndFlavour(cat, flavour);
    }
}
