package com.felzan.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class CoffeeBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coffeeBeanId;

    private String name;
    private String description;
    private double price;
    private int stockQuantity;

}