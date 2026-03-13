package org.example.repository;

import org.example.entity.Order;
import org.example.entity.Product;

import java.util.*;

public class OrderMapRepo implements OrderRepoInterface{

    private final Map< Integer, Order> orders;

    public OrderMapRepo() {
        orders = new HashMap<>();
    }

    public OrderMapRepo(Map<Integer, Order> orders) {
        this.orders = orders;
    }

    @Override
    public void add(Order order) {
        Objects.requireNonNull(order, "order must not be null");
        orders.put(order.id(), order);
    }

    @Override
    public void deleteById(int id) {
        orders.remove(id);
    }

    @Override
    public Optional<Order> findById(int id) {
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
