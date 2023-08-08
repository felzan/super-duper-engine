package com.felzan.ecommerce.resource.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {

    private Long coffeeBeanId;
    private Integer quantity;
    private BigDecimal price;
}
