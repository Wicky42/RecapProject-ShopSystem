package org.example.repository;

import org.example.domain.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrderListRepo implements OrderRepository {

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
    public void deleteById(String id){
        orders.removeIf(o -> o.id().equals(id));
    }

    @Override
    public Optional<Order> findById(String id){
        return orders.stream().filter(o -> o.id().equals(id)).findFirst();
    }

    @Override
    public List<Order> findAll(){
        return List.copyOf(orders);
    }

    @Override
    public void update(Order shippedOrder) {
        orders.removeIf(o -> o.id().equals( shippedOrder.id()));
        orders.add(shippedOrder);
    }

}
