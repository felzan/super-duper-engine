package com.felzan.ecommerce.resource;

import com.felzan.ecommerce.domain.Order;
import com.felzan.ecommerce.resource.dto.OrderDTO;
import com.felzan.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> post(@RequestBody OrderDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAll() {
        List<Order> all = service.getAll();
        return ResponseEntity.ok(all);
    }
}
