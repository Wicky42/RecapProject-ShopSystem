package org.example.repository;

import org.example.domain.Order;
import org.example.domain.OrderItem;
import org.example.domain.OrderStatus;
import org.example.domain.Product;
import org.example.domain.exceptions.OrderNotFoundException;
import org.example.service.IdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    OrderMapRepo repo;
    IdService idService;

    @BeforeEach
    void init(){
        repo = new OrderMapRepo();
        idService = new IdService();

    }

    @Test
    void update_shouldNotChangeTimeStamp() {
        Product product1 = new Product(idService.newProductId(), "product", BigDecimal.TEN, 10);
        Order order = new Order(idService.newOrderId(), List.of(new OrderItem(product1, 2)), ZonedDateTime.now(), OrderStatus.PROCESSING);
        repo.add(order);
        repo.update(order);
        Order updatesOrder = repo.findById(order.id()).orElseThrow(OrderNotFoundException::new);
        assertTrue(order.timeStamp().isEqual(updatesOrder.timeStamp()));
    }
}