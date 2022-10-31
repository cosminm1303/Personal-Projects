package com.example.onlineshop.controllers;

import com.example.onlineshop.DTO.ProductDTO;
import com.example.onlineshop.models.Product;
import com.example.onlineshop.service.CategoryServiceImp;
import com.example.onlineshop.service.ProductServiceImpl;
import com.example.onlineshop.service.SubCatServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class ProductController {

    private ProductServiceImpl productService;
    private CategoryServiceImp categoryServiceImp;
    private SubCatServiceImpl subCatService;

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "adminProduct";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProd(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/product/update/{id}/{type}")
    public String updateProd(@PathVariable Long id,
                             @PathVariable String type,
                             Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.get().getId());
            productDTO.setName(product.get().getName());
            productDTO.setDescription(product.get().getDescription());
            productDTO.setPrice(product.get().getPrice());
            productDTO.setCategoryId(product.get().getCategory().getId());
            productDTO.setSubcategoryId(product.get().getSubCategory().getId());
            productDTO.setFlavour(product.get().getFlavour());
            productDTO.setWeight(product.get().getWeight());
            productDTO.setPresentation(product.get().getPresentation());
            productDTO.setSize(product.get().getSize());
            productDTO.setColor(product.get().getColor());
            productDTO.setGenre(product.get().getGenre());

            String[] types = type.split(" ");
            String typeF = types[0].toLowerCase();
            model.addAttribute("type", typeF);
            model.addAttribute("categories", categoryServiceImp.getAllCategories());
            model.addAttribute("subCategories", subCatService.getAllSubCategories());
            model.addAttribute("productDTO", productDTO);

            return "addProd";
        } else
            return "404";
    }

    @ModelAttribute("productDTO")
    public ProductDTO productDTO() {
        return new ProductDTO();
    }

    @GetMapping("/product/add/{type}")
    public String getAddRender(@PathVariable String type, Model model) {
        model.addAttribute("type", type);
        model.addAttribute("categories", categoryServiceImp.getAllCategories());
        model.addAttribute("subCategories", subCatService.getAllSubCategories());
        return "addProd";
    }

    @PostMapping("/product/add/{type}")
    @Transactional
    public String addProd(@Valid @ModelAttribute("productDTO") ProductDTO productDTO, Errors errors,
                          @PathVariable String type,
                          @RequestParam("prodImg") MultipartFile file,
                          @RequestParam(required = false, name = "imgProd") String imgName,
                          Model model) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("type", type);
            model.addAttribute("categories", categoryServiceImp.getAllCategories());
            model.addAttribute("subCategories", subCatService.getAllSubCategories());
            return "/addProd";
        }
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setStock(productDTO.getStock());
        product.setPrice(productDTO.getPrice());
        product.setFlavour(productDTO.getFlavour());
        product.setWeight(productDTO.getWeight());
        product.setPresentation(productDTO.getPresentation());
        product.setColor(productDTO.getColor());
        product.setSize(productDTO.getSize());
        product.setGenre(product.getGenre());
        product.setCategory(categoryServiceImp.getCategoryById(productDTO.getCategoryId()).get());
        product.setSubCategory(subCatService.getSubCategoryById(productDTO.getSubcategoryId()).get());
        String imageUUID;
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
        } else {
            imageUUID = imgName;
        }
        product.setPicture(imageUUID);
        productService.saveProduct(product);
        return "redirect:/admin";
    }
}
