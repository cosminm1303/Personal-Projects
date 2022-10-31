package com.example.onlineshop.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartItem {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int quantity;


}
