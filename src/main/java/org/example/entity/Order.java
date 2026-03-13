package org.example.entity;

import java.math.BigDecimal;
import java.util.List;

public record Order(
        int id,
        List<OrderItem> items,
        OrderStatus orderStatus
) {

    public BigDecimal total(){
        return items.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order ship(){
        return new Order(id, items, orderStatus.ship());
    }

    public Order complete(){
        return new Order(id, items, orderStatus.complete());
    }
}
