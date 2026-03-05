package org.example.entity;

import java.math.BigDecimal;
import java.util.List;

public record Order(
        int id,
        List<Product> products
) {

    public BigDecimal total(){
        return products.stream()
                .map(Product::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
