package com.example.Spring_Application.Model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ingrediente")
public class Ingredient {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String type;

}
