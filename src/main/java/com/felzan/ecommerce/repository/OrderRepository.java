package com.felzan.ecommerce.repository;

import com.felzan.ecommerce.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends ListCrudRepository<Order, Long> {

    @Query("""
            SELECT
            DISTINCT o
            FROM Order o JOIN FETCH o.items
            """)
    List<Order> findOrderWithItems();
}
