package com.example.onlineshop.service;

import com.example.onlineshop.models.OrderProduct;
import com.example.onlineshop.repository.OrderProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderProductService {
    private OrderProductRepo orderProductRepo;

    public void saveOrderProduct(OrderProduct orderProduct){
        orderProductRepo.save(orderProduct);
    }
}
