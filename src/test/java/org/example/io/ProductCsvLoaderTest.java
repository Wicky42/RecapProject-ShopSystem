package org.example.io;

import org.example.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ProductCsvLoaderTest {

    ProductCsvLoader loader;

    @BeforeEach
    void init(){
        loader = new ProductCsvLoader();
    }

    @Test
    void load_shouldReadProductsFromCsv() throws IOException {
        List<Product> products = loader.load("productCatalog.csv");

        assertThat(products)
                .isNotEmpty()
                .hasSize(34)
                .contains(new Product(1001, "Repair Shampoo Argan Oil", new BigDecimal("4.99"), 99))
                .contains(new Product(8004, "Baby Diapers Size 2 (44pcs)", new BigDecimal("9.49"), 12));
    }

    @Test
    void load_shouldThrowException_whenResourceDoesNotExist() {
        assertThatThrownBy(() -> loader.load("does-not-exist.csv"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Resource not found");
    }

    @Test
    void load_shouldThrowException_whenCsvContainsInvalidPrice() {

        ProductCsvLoader loader = new ProductCsvLoader();

        assertThatThrownBy(() -> loader.load("broken-products.csv"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}