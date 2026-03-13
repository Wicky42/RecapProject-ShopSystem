package org.example.repository;

import org.example.domain.Order;

import java.util.*;

public class OrderMapRepo implements OrderRepository {

    private final Map< String, Order> orders;

    public OrderMapRepo() {
        orders = new HashMap<>();
    }

    public OrderMapRepo(Map<String, Order> orders) {
        this.orders = orders;
    }

    @Override
    public void add(Order order) {
        Objects.requireNonNull(order, "order must not be null");
        orders.put(order.id(), order);
    }

    @Override
    public void deleteById(String id) {
        orders.remove(id);
    }

    @Override
    public Optional<Order> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<Order> findAll() {
        return List.copyOf(orders.values());
    }

    @Override
    public void update(Order order) {
        orders.put(order.id(), order);
    }
}
