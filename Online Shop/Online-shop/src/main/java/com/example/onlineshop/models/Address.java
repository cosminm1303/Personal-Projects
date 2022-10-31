package com.example.onlineshop.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String street;
    private Integer number;
    private String city;
    private String county;
    private Integer postalCode;
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
