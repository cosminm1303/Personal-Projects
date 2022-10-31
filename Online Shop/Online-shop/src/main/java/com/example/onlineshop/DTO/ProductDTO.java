package com.example.onlineshop.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ProductDTO {

    private Long id;
    @NotEmpty(message = "This field must not be empty!")
    private String name;
    @NotEmpty(message = "This field must not be empty!")
    private String description;
    @NotEmpty(message = "This field must not be empty!")
    private String stock;
    private Double price;
    private String flavour;
    private Integer weight;
    private String presentation;
    private String size;
    private String color;
    private String genre;
    private Long categoryId;
    private Long SubcategoryId;
}
