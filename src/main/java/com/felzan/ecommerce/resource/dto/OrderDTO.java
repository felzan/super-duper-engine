package com.felzan.ecommerce.resource.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {

    private List<OrderItemDTO> items = new ArrayList<>();
    private Long userId;
    private BigDecimal totalPrice;
    private String shippingAddress;
}
