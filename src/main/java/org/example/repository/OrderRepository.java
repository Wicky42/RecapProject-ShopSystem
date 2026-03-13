package org.example.repository;

import org.example.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    void add(Order order);

    void deleteById(String id);

    Optional<Order> findById(String id);
    List<Order> findAll();

    void update(Order shippedOrder);
}
