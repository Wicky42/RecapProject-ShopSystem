package org.example.ui;

import org.example.domain.Product;

import java.math.BigDecimal;

public record CartEntry(
        Product product,
        int quantity
) {

    public BigDecimal subtotal() {
        return product.price().multiply(BigDecimal.valueOf(quantity));
    }
}