package com.example.onlineshop.repository;

import com.example.onlineshop.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findAllByUserId(Long id);
}
