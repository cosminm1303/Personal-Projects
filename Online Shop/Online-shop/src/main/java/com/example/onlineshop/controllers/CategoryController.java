package com.example.onlineshop.controllers;

import com.example.onlineshop.models.Category;
import com.example.onlineshop.service.CategoryServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/category")
public class CategoryController {

    private CategoryServiceImp categoryServiceImp;

    @GetMapping
    public String allCat(Model model) {
        model.addAttribute("categories", categoryServiceImp.getAllCategories());
        return "allCategories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCat(@PathVariable Long id) {
        categoryServiceImp.deleteById(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/update/{id}")
    public String updateCat(@PathVariable Long id, Model model) {
        Optional<Category> optionalCategory = categoryServiceImp.getCategoryById(id);
        if (optionalCategory.isPresent()) {
            model.addAttribute("category", optionalCategory.get());
            return "addCat";
        } else
            return "404";
    }


    @GetMapping("/add")
    public String renderAddCat(Model model) {
        model.addAttribute("category", new Category());
        return "addCat";
    }

    @PostMapping("/add")
    public String addCat(@Valid @ModelAttribute("category") Category category, Errors errors) {
        if (errors.hasErrors()) {
            return "addCat";
        }
        categoryServiceImp.saveCategory(category);
        return "redirect:";
    }
}
