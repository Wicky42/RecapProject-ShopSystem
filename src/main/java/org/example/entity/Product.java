package org.example.entity;

import java.math.BigDecimal;

public record Product(
        int id,
        String name,
        BigDecimal price,
        int availablitity
) { }
