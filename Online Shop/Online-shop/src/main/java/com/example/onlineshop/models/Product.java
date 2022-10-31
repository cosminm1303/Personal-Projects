package com.example.onlineshop.models;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String stock;
    private String picture;
    private Double price;
    private String flavour;
    private Integer weight;
    private String presentation;
    private String size;
    private String color;
    private String genre;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubCategory subCategory;
}
