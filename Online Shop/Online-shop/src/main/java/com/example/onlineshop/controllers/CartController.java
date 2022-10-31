package com.example.onlineshop.controllers;

import com.example.onlineshop.models.CartItem;
import com.example.onlineshop.models.MyUserDetails;
import com.example.onlineshop.models.Product;
import com.example.onlineshop.service.CategoryServiceImp;
import com.example.onlineshop.service.ProductServiceImpl;
import com.example.onlineshop.service.ShoppingCartService;
import com.example.onlineshop.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CartController {
    private CategoryServiceImp categoryServiceImp;
    private ProductServiceImpl productService;
    private ShoppingCartService shoppingCartService;
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/cart")
    public String getCart(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        List<CartItem> cartItems = shoppingCartService.findByUser(userDetailsService.getUserByEmail(userDetails.getUsername()));
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "cart";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        shoppingCartService.deleteByIdUACI(id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update/{id}")
    public String updateCart(@PathVariable Long id, @RequestParam Integer quantity) {
        Optional<CartItem> cartItem = shoppingCartService.findById(id);
        Optional<Product> product = productService.getProductById(cartItem.get().getProduct().getId());
        if (Integer.parseInt(product.get().getStock()) >= quantity) {
            if (cartItem.isPresent()) {
                CartItem cartItem1 = new CartItem();
                cartItem1.setId(cartItem.get().getId());
                cartItem1.setUser(cartItem.get().getUser());
                cartItem1.setQuantity(quantity);
                cartItem1.setProduct(cartItem.get().getProduct());
                shoppingCartService.addProduct(cartItem1);
            }
            return "redirect:/cart";
        } else {
            return "redirect:/cart?stock";
        }
    }
}
