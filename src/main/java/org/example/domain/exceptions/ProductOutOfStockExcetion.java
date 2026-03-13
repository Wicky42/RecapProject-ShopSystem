package org.example.domain.exceptions;

public class ProductOutOfStockExcetion extends RuntimeException {
    public ProductOutOfStockExcetion(String message) {
        super(message);
    }
}
