package com.example.onlineshop.DTO;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String street;
    private Integer number;
    private String city;
    private String county;
    private Integer postalCode;
    private String country;
}
