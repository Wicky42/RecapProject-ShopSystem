package org.example.entity;

import java.math.BigDecimal;

public record Product(
        int id,
        String name,
        BigDecimal price,
        int availablitity
) {
    public Product sell(int amount){
        if(amount > availablitity){
            throw new IllegalArgumentException("Not enough in stock");
        }
        return new Product(id, name, price, (availablitity-amount));
    }
}
