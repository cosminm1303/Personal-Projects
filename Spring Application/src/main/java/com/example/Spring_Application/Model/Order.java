package com.example.Spring_Application.Model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="order_details")
public class Order{
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotBlank(message="Delivery name is required")
    private String deliveryName;

    @Column
    @NotBlank(message="State is required")
    private String deliveryState;

    @Column
    @NotBlank(message="City is required")
    private String deliveryCity;

    @Column
    @NotBlank(message="Street is required")
    private String deliveryStreet;

    @Column
    @NotBlank(message="Zip code is required")
    private String deliveryZip;

    @Column
    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;

    @Column
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message="Must be formatted MM/YY")
    private String ccExpiration;

    @Column
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @ManyToMany(targetEntity=Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

}
