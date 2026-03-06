package org.example.io;

import org.example.entity.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

public class ProductCsvLoader {

    public List<Product> load(String resourceName) throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);

        if (is == null) {
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines()
                    .skip(1)
                    .map(this::parse)
                    .toList();
        }
    }

    private Product parse(String line) {
        String[] parts = line.split(",");

        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        BigDecimal price = new BigDecimal(parts[2]);

        return new Product(id, name, price);
    }
}