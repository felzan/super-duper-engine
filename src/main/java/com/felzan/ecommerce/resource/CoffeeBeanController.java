package com.felzan.ecommerce.resource;

import com.felzan.ecommerce.domain.CoffeeBean;
import com.felzan.ecommerce.service.CoffeeBeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class CoffeeBeanController {
    private final CoffeeBeanService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoffeeBean> post(@RequestBody CoffeeBean coffeeBean) {
        return ResponseEntity.ok(service.createCoffeeBean(coffeeBean));
    }

    @GetMapping()
    public ResponseEntity<List<CoffeeBean>> getAll() {
        return ResponseEntity.ok(service.getAllCoffeeBeans());
    }

    @GetMapping("{id}")
    public ResponseEntity<CoffeeBean> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCoffeeBeanById(id));
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoffeeBean> update(@RequestBody CoffeeBean coffeeBean, @PathVariable Long id) {
        coffeeBean.setCoffeeBeanId(id);
        return ResponseEntity.ok(service.updateCoffeeBean(coffeeBean));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteCoffeeBean(id);
        return ResponseEntity.ok().build();
    }
}
