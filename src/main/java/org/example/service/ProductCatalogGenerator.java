package org.example.service;

import org.example.domain.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductCatalogGenerator {

    private final IdService idService;
    private final Random random;

    public ProductCatalogGenerator(IdService idService) {
        this(idService, new Random());
    }

    public ProductCatalogGenerator(IdService idService, Random random) {
        this.idService = idService;
        this.random = random;
    }

    public List<Product> generate(int amount) {
        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= amount; i++) {
            products.add(new Product(
                    idService.newProductId(),
                    "Produkt-" + i,
                    randomPrice(),
                    randomAvailability()
            ));
        }

        return products;
    }

    private BigDecimal randomPrice() {
        double value = 5 + (200 * random.nextDouble());
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    private int randomAvailability() {
        return random.nextInt(21); // 0 bis 20
    }
}