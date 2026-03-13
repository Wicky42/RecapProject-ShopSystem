package org.example.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record Order(
        String id,
        List<OrderItem> items,
        ZonedDateTime timeStamp,
        OrderStatus status
) {

    public BigDecimal total(){
        return items.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order ship(){
        return new Order(id, items, timeStamp, status.ship());
    }

    public Order complete(){
        return new Order(id, items, timeStamp, status.complete());
    }
}
