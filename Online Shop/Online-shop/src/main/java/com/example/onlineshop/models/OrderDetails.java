package com.example.onlineshop.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private OrderAddress orderAddress;

    @ManyToMany
    @JoinTable(
            name = "orderDetails_orderProducts",
            joinColumns = @JoinColumn(name = "orderDetails_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "orderProducts_id", referencedColumnName = "id")
    )
    private Collection<OrderProduct> orderProducts;

    private String payType;

    private String transportType;

    private LocalDate time;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
