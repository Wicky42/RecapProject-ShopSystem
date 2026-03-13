package org.example.entity;

import java.math.BigDecimal;
import java.util.List;

public record Order(
        int id,
        List<OrderItem> items
) {

    public BigDecimal total(){
        return items.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
