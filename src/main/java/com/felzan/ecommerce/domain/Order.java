package com.felzan.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.felzan.ecommerce.domain.OrderStatus.PLACED;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne
    private User user;

    @Builder.Default
    @Temporal(TemporalType.DATE)
    private Date orderDate = Date.valueOf(LocalDate.now());

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OrderStatus status = PLACED;

    private double totalPrice;
    private String shippingAddress;

}