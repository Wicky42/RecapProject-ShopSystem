package org.example.service;

import org.example.entity.Order;
import org.example.entity.Product;
import org.example.repository.OrderListRepo;
import org.example.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    ProductRepo productRepo;
    OrderListRepo orderRepo;
    ShopService service;


    @BeforeEach
    void setup(){
        productRepo = new ProductRepo();
        orderRepo = new OrderListRepo();
        service = new ShopService(productRepo, orderRepo);
    }

    @Test
    void newOrder_shouldIncreaseOrderRepo_whenPlacedAValidOrder(){
        productRepo.add(new Product(1, "Laptop", BigDecimal.TEN));
        Order order = service.newOrder(1);
        assertEquals(1, order.products().size());
    }

    @Test
    void newOrder_shouldThrowException_whenProductMissing(){
        assertThrows(IllegalArgumentException.class , () -> service.newOrder(99));
    }

}