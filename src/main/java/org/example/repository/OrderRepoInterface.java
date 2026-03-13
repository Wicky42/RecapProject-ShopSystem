package org.example.repository;

import org.example.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepoInterface {

    void add(Order order);

    void deleteById(int id);

    Optional<Order> findById(int id);
    List<Order> findAll();

    void update(Order shippedOrder);
}
