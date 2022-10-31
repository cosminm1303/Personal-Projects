package com.example.onlineshop.repository;

import com.example.onlineshop.models.CartItem;
import com.example.onlineshop.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends CrudRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    void deleteByUserId(Long id);
}
