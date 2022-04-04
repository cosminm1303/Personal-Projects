package com.example.Spring_Application.Controller;

import com.example.Spring_Application.Model.Ingredient;
import com.example.Spring_Application.Model.Order;
import com.example.Spring_Application.Model.Taco;
import com.example.Spring_Application.Service.IngredientServiceImpl;
import com.example.Spring_Application.Service.TacoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignController {
    private final IngredientServiceImpl ingredientService;
    private final TacoServiceImpl tacoService;

    @Autowired
    public DesignController(IngredientServiceImpl ingredientService,TacoServiceImpl tacoService){
        this.ingredientService=ingredientService;
        this.tacoService=tacoService;
    }

    @ModelAttribute(name = "design")
    public Taco design() {
        return new Taco();
    }
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @GetMapping
    public String showDesign(Model model){

        List<Ingredient> ingredients=ingredientService.getAllIngredients();

        for(Ingredient ingredient:ingredients){
            model.addAttribute(ingredient.getType(),filterByType(ingredients,ingredient.getType()));
        }
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco taco, BindingResult bindingResult, @ModelAttribute Order order,Model model){

        if (bindingResult.hasErrors()) {
            List<Ingredient> ingredients=ingredientService.getAllIngredients();

            for(Ingredient ingredient:ingredients){
                model.addAttribute(ingredient.getType(),filterByType(ingredients,ingredient.getType()));
            }
            return "design";
        }

        Taco saved=tacoService.saveAllTacos(taco);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients,String type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
