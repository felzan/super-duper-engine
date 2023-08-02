package com.felzan.ecommerce.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    private Order order;

    @ManyToOne
    private CoffeeBean coffeeBean;

    private int quantity;
    private double itemPrice;

}