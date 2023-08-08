package com.felzan.ecommerce.service;

import com.felzan.ecommerce.domain.Order;
import com.felzan.ecommerce.domain.OrderItem;
import com.felzan.ecommerce.domain.User;
import com.felzan.ecommerce.repository.OrderRepository;
import com.felzan.ecommerce.resource.dto.OrderDTO;
import com.felzan.ecommerce.resource.dto.OrderItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    private final CoffeeBeanService coffeeBeanService;
    private final OrderRepository repository;

    public OrderDTO create(OrderDTO dto) {
        List<OrderItem> items = new ArrayList<>();

        dto.getItems().forEach(item -> {
            OrderItem orderItem = OrderItem.builder()
                    .coffeeBean(coffeeBeanService.getCoffeeBeanById(item.getCoffeeBeanId()))
                    .quantity(item.getQuantity())
                    .itemPrice(item.getPrice().doubleValue())
                    .build();
            items.add(orderItem);
        });

        User user = userService.getUserById(dto.getUserId());
        Order order = Order.builder()
                .items(items)
                .user(user)
                .totalPrice(dto.getTotalPrice().doubleValue())
                .shippingAddress(dto.getShippingAddress())
                .build();
        items.forEach(item -> item.setOrder(order));
        Order saved = repository.save(order);
        return dto;
    }

    public List<Order> getAll() {
        return repository.findOrderWithItems();
    }
}
