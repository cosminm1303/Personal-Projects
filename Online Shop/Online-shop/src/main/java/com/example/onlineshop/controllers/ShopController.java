package com.example.onlineshop.controllers;

import com.example.onlineshop.models.CartItem;
import com.example.onlineshop.models.MyUserDetails;
import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;
import com.example.onlineshop.service.CategoryServiceImp;
import com.example.onlineshop.service.ProductServiceImpl;
import com.example.onlineshop.service.ShoppingCartService;
import com.example.onlineshop.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping
public class ShopController {

    private CategoryServiceImp categoryServiceImp;
    private ProductServiceImpl productService;
    private ShoppingCartService shoppingCartService;
    private UserDetailsServiceImpl userDetailsService;

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/shop/{cat}/{sCat}")
    public String getProducts(@PathVariable String sCat,
                              @PathVariable String cat,
                              Model model) {
        List<Product> products = productService.findByCategoryName(cat);
        Set<String> subCategories = new TreeSet<>();
        Set<String> flavors = new TreeSet<>();
        for (Product product : products) {
            subCategories.add(product.getSubCategory().getName());
            if (!product.getFlavour().equals(""))
                flavors.add(product.getFlavour());
        }
        model.addAttribute("cat", cat);
        model.addAttribute("categories", subCategories);
        model.addAttribute("flavors", flavors);
        model.addAttribute("products", productService.findBySubCategoryName(sCat).stream().filter(distinctByKey(Product::getName)).collect(Collectors.toList()));
        return "products";
    }

    @GetMapping("/shop/category/{cat}/{sCat}")
    public String getProductByCategory(Model model, @PathVariable String cat, @PathVariable String sCat) {
        List<Product> products = productService.findByCategoryName(cat);
        Set<String> subCategories = new TreeSet<>();
        Set<String> flavors = new TreeSet<>();
        for (Product product : products) {
            subCategories.add(product.getSubCategory().getName());
            if (!product.getFlavour().equals(""))
                flavors.add(product.getFlavour());
        }
        model.addAttribute("categories", subCategories);
        model.addAttribute("products", productService.findBySubCategoryName(sCat).stream().filter(distinctByKey(Product::getName)).collect(Collectors.toList()));
        model.addAttribute("flavors", flavors);
        return "products";
    }

    @GetMapping("/shop/flavors/{cat}/{flavor}")
    public String getProductsByFlavors(@PathVariable String cat,
                                       @PathVariable String flavor,
                                       Model model) {
        List<Product> products = productService.findByCatAndFlavour(cat, flavor);
        List<Product> products1 = productService.findByCategoryName(cat);
        Set<String> subCategories = new TreeSet<>();
        Set<String> flavors = new TreeSet<>();
        for (Product product : products1) {
            subCategories.add(product.getSubCategory().getName());
            if (!product.getFlavour().equals(""))
                flavors.add(product.getFlavour());
        }
        model.addAttribute("categories", subCategories);
        model.addAttribute("products", products.stream().filter(distinctByKey(Product::getName)).collect(Collectors.toList()));
        model.addAttribute("flavors", flavors);
        return "products";

    }

    @GetMapping("/shop/details/{prodId}")
    public String showDetails(@PathVariable Long prodId, Model model) {
        Optional<Product> product = productService.getProductById(prodId);
        List<Product> productList = productService.findByName(product.get().getName());
        model.addAttribute("product", product.get());
        model.addAttribute("productListFlav", productList.stream().filter(distinctByKey(Product::getFlavour)).collect(Collectors.toList()));
        model.addAttribute("productListWeig", productList.stream().filter(distinctByKey(Product::getWeight)).collect(Collectors.toList()));
        model.addAttribute("productListPres", productList.stream().filter(distinctByKey(Product::getPresentation)).collect(Collectors.toList()));
        return "productDetails";
    }

    @PostMapping("/cart/add/{prodId}")
    public String addItemToCart(@PathVariable Long prodId,
                                @RequestParam String flavour,
                                @RequestParam Integer weight,
                                @RequestParam String presentation,
                                @RequestParam int quantity,
                                @AuthenticationPrincipal MyUserDetails myUserDetails,
                                Model model) {
        int ok = 0;
        List<CartItem> cartItems = shoppingCartService.getAll();
        User user = userDetailsService.getUserByEmail(myUserDetails.getUsername());
        Optional<Product> product = productService.getProductById(prodId);
        Product produs = productService.findByFWP(product.get().getName(), flavour, weight, presentation);
        if (produs != null) {
            if (Integer.parseInt(produs.getStock()) >= quantity) {
                for (CartItem cartItem : cartItems) {
                    if (Objects.equals(cartItem.getProduct().getId(), produs.getId())) {
                        cartItem.setQuantity(cartItem.getQuantity() + quantity);
                        shoppingCartService.addProductByCartItem(cartItem);
                        ok = 1;
                        break;
                    }
                }
                if (ok == 0) {
                    shoppingCartService.addProduct(produs.getId(), quantity, user);
                }
                return "redirect:/shop/details/" + product.get().getId() + "?success";
            } else {
                return "redirect:/shop/details/" + product.get().getId() + "?stock";
            }
        } else {
            return "redirect:/shop/details/" + product.get().getId() + "?stock";
        }
    }

}
