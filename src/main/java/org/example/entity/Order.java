package org.example.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record Order(
        int id,
        List<OrderItem> items,
        ZonedDateTime timeStamp,
        OrderStatus orderStatus
) {

    public BigDecimal total(){
        return items.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order ship(){
        return new Order(id, items, timeStamp, orderStatus.ship());
    }

    public Order complete(){
        return new Order(id, items, timeStamp, orderStatus.complete());
    }
}
