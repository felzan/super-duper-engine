package com.felzan.ecommerce.service;

import com.felzan.ecommerce.domain.CoffeeBean;
import com.felzan.ecommerce.repository.CoffeeBeanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoffeeBeanService {
    private final CoffeeBeanRepository repository;

    public CoffeeBean createCoffeeBean(CoffeeBean coffeeBean) {
        return repository.save(coffeeBean);
    }

    public CoffeeBean getCoffeeBeanById(Long coffeeBeanId) {
        return repository.findById(coffeeBeanId).orElse(null);
    }

    public List<CoffeeBean> getAllCoffeeBeans() {
        return repository.findAll();
    }

    public CoffeeBean updateCoffeeBean(CoffeeBean coffeeBean) {
        return repository.save(coffeeBean);
    }

    public void deleteCoffeeBean(Long coffeeBeanId) {
        repository.deleteById(coffeeBeanId);
    }
}
