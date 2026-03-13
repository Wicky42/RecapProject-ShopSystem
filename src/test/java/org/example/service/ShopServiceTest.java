package org.example.service;

import org.example.entity.Order;
import org.example.entity.OrderStatus;
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
        productRepo.add(new Product(1, "Laptop", BigDecimal.TEN, 99));
        Order order = service.newOrder(1);
        assertEquals(1, order.items().size());
    }

    @Test
    void newOrder_shouldThrowException_whenProductMissing(){
        assertThrows(IllegalArgumentException.class , () -> service.newOrder(99));
    }

    @Test
    void getOrdersWithStatus_shouldreturnAListOfOrdersWithCallesStatus(){
        productRepo.add(new Product(1, "Laptop", BigDecimal.TEN, 99));
        productRepo.add(new Product(2, "Phone", BigDecimal.valueOf(699.99), 99));
        Order order1 = service.newOrder(1);
        Order order2= service.newOrder(2);

        assertEquals(2, service.getOrdersWithStatus(OrderStatus.PROCESSING).size());
        assertTrue(service.getOrdersWithStatus(OrderStatus.PROCESSING).contains(order1));
        assertTrue(service.getOrdersWithStatus(OrderStatus.PROCESSING).contains(order2));
    }

    @Test
    void shipOrder_shouldChangeOrderStatusOfOrder(){
        productRepo.add(new Product(1, "Laptop", BigDecimal.TEN, 99));
        Order order = service.newOrder(1);
        Order shipped = service.shipOrder(order.id());
        assertEquals(1, service.getOrdersWithStatus(OrderStatus.IN_DELIVERY).size());
        assertTrue(service.getOrdersWithStatus(OrderStatus.IN_DELIVERY).contains(shipped));
    }

}