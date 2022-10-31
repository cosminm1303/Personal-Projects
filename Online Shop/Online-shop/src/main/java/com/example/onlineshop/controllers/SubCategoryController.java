package com.example.onlineshop.controllers;

import com.example.onlineshop.models.SubCategory;
import com.example.onlineshop.service.SubCatServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/subCategory")
@AllArgsConstructor
public class SubCategoryController {

    private SubCatServiceImpl subCatService;

    @GetMapping
    public String allSubCat(Model model) {
        model.addAttribute("subCategories", subCatService.getAllSubCategories());
        return "allSubCategories";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubCat(@PathVariable Long id) {
        subCatService.deleteById(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/update/{id}")
    public String updateSubCat(@PathVariable Long id, Model model) {
        Optional<SubCategory> optionalCategory = subCatService.getSubCategoryById(id);
        if (optionalCategory.isPresent()) {
            model.addAttribute("category", optionalCategory.get());
            return "addSubCat";
        } else
            return "404";
    }


    @GetMapping("/add")
    public String renderAddCat(Model model) {
        model.addAttribute("subCategory", new SubCategory());
        return "addSubCat";
    }

    @PostMapping("/add")
    public String addCat(@Valid @ModelAttribute("subCategory") SubCategory subCategory, Errors errors) {
        if (errors.hasErrors()) {
            return "addSubCat";
        }
        subCatService.saveSubCategory(subCategory);
        return "redirect:";
    }
}
