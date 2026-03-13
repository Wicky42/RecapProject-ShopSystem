package org.example.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void ship_shouldThrowException_whenCalledWithWrongStatus() {
        Product p = new Product("1","Product", BigDecimal.TEN, 99);
        OrderItem oi = new OrderItem(p, 2);
        Order order = new Order(1, List.of(oi), ZonedDateTime.now(), OrderStatus.IN_DELIVERY);

        assertThrows(IllegalStateException.class, order::ship);
    }

    @Test
    void completes_shouldThrowException_whenCalledWithWrongStatus() {
        Product p = new Product("1","Product", BigDecimal.TEN, 99);
        OrderItem oi = new OrderItem(p, 2);
        Order order = new Order(1, List.of(oi), ZonedDateTime.now(), OrderStatus.PROCESSING);

        assertThrows(IllegalStateException.class, order::complete);
    }
}