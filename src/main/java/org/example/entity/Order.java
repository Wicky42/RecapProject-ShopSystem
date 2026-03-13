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
        if( orderStatus != OrderStatus.PROCESSING){
            throw new IllegalStateException("Nur Bestellungen in Verarbeitung können verschickt werden");
        }
        return new Order(id, items, OrderStatus.IN_DELIVERY);
    }

    public Order complete(){
        if( orderStatus != OrderStatus.IN_DELIVERY){
            throw new IllegalStateException("Nur Bestellungen in Zusellung können abgeschlossen werden");
        }
        return new Order(id, items, OrderStatus.COMPLETED);
    }
}
