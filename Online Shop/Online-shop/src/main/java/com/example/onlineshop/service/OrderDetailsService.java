package com.example.onlineshop.service;

import com.example.onlineshop.models.OrderDetails;
import com.example.onlineshop.repository.OrderDetailsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailsService {
    OrderDetailsRepo orderDetailsRepo;

    public void saveOrder(OrderDetails orderDetails) {
        orderDetailsRepo.save(orderDetails);
    }

    public List<OrderDetails> getOrders(){
        return orderDetailsRepo.findAll();
    }
}
