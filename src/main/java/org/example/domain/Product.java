package org.example.domain;

import org.example.domain.exceptions.ProductOutOfStockExcetion;

import java.math.BigDecimal;

public record Product(
        String id,
        String name,
        BigDecimal price,
        int availablitity
) {
    public Product sell(int amount){
        if( amount <= 0 ){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if(amount > availablitity){
            throw new ProductOutOfStockExcetion("Product " + id + " out of stock");
        }
        return new Product(id, name, price, (availablitity-amount));
    }
}
