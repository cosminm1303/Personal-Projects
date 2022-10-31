package com.example.onlineshop.service;

import com.example.onlineshop.models.CartItem;
import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;
import com.example.onlineshop.repository.CartItemRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShoppingCartService {
    private CartItemRepo cartItemRepo;
    private ProductServiceImpl productService;

    public void addProductByCartItem(CartItem cartItem) {
        cartItemRepo.save(cartItem);
    }

    public CartItem addProduct(CartItem cartItem) {
        return cartItemRepo.save(cartItem);
    }

    public void addProduct(Long prodId, int quantity, User user) {
        Product product = productService.getProductById(prodId).get();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setUser(user);
        cartItemRepo.save(cartItem);
    }

    public List<CartItem> findByUser(User user) {
        return cartItemRepo.findByUser(user);
    }

    public void deleteByUserId(Long id){
        cartItemRepo.deleteByUserId(id);
    }
    public void deleteByIdUACI(Long id) {
        cartItemRepo.deleteById(id);
    }

    public List<CartItem> getAll() {
        return (List<CartItem>) cartItemRepo.findAll();
    }

    public Optional<CartItem> findById(Long id) {
        return cartItemRepo.findById(id);
    }

}
