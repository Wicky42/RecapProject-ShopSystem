package org.example.repository;

import org.example.entity.Order;
import org.example.entity.OrderItem;
import org.example.entity.OrderStatus;
import org.example.entity.Product;
import org.example.entity.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    OrderMapRepo repo;

    @BeforeEach
    void init(){
        repo = new OrderMapRepo();
    }

    @Test
    void update_shouldNotChangeTimeStamp() {
        Product product1 = new Product(1,"product", BigDecimal.TEN, 10);
        Order order = new Order(1, List.of(new OrderItem(product1, 2)), ZonedDateTime.now(), OrderStatus.PROCESSING);
        repo.add(order);
        repo.update(order);
        Order updatesOrder = repo.findById(order.id()).orElseThrow(OrderNotFoundException::new);
        assertTrue(order.timeStamp().isEqual(updatesOrder.timeStamp()));
    }
}