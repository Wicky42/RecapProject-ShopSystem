package org.example.repository;

import org.example.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrderListRepo implements OrderRepoInterface{

    private final List<Order> orders;

    public OrderListRepo() {
        orders = new ArrayList<>();
    }

    public OrderListRepo(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void add(Order order){
        Objects.requireNonNull(order, "order must not be null");
        orders.add(order);
    }
    @Override
    public void deleteById(int id){
        orders.removeIf(o -> o.id() == id);
    }

    @Override
    public Optional<Order> findById(int id){
        return orders.stream().filter(o -> o.id() == id).findFirst();
    }

    @Override
    public List<Order> findAll(){
        return List.copyOf(orders);
    }

}
