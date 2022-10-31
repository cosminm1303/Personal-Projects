package com.example.onlineshop.models;

import com.spire.ms.System.DateTime;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class OrderAddress {
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
