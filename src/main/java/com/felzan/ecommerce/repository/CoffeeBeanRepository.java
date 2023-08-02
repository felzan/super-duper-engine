package com.felzan.ecommerce.repository;

import com.felzan.ecommerce.domain.CoffeeBean;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeBeanRepository extends ListCrudRepository<CoffeeBean, Long> {
}
