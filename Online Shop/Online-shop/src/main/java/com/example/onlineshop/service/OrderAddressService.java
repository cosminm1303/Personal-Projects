package com.example.onlineshop.service;

import com.example.onlineshop.models.OrderAddress;
import com.example.onlineshop.repository.OrderAddressRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderAddressService {
    private OrderAddressRepo orderAddressRepo;

    public void saveOrderAddress(OrderAddress orderAddress){
        orderAddressRepo.save(orderAddress);
    }
}
