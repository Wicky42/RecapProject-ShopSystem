package org.example.service;

import org.example.domain.Order;
import org.example.domain.OrderItem;
import org.example.domain.OrderStatus;
import org.example.domain.Product;
import org.example.domain.exceptions.OrderNotFoundException;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepo;

import java.time.ZonedDateTime;
import java.util.*;

public class ShopService {

    private final ProductRepo productRepo;
    private final OrderRepository orderRepo;

//    private static int nextOrderId = 12345;

    private final IdService idService;

    public ShopService(ProductRepo productRepo, OrderRepository orderRepo, IdService idService) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.idService = idService;
    }

    public void addProduct(Product product){
        productRepo.add(product);
    }

    public Order newOrder(String... ids) {
        Map<String, Integer> quantities = new HashMap<>();

        for (String id : ids) {
            quantities.merge(id, 1, Integer::sum);
        }

        List<OrderItem> items = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : quantities.entrySet()) {

            String id = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

            Product updatedProduct = product.sell(quantity);
            productRepo.update(updatedProduct);

            items.add(new OrderItem(product, quantity));
        }

        Order order = new Order(idService.newOrderId(), items, ZonedDateTime.now(), OrderStatus.PROCESSING);
        orderRepo.add(order);

        return order;
    }


    public List<Order> getOrdersWithStatus(OrderStatus orderStatus) {
        return orderRepo.findAll().stream()
                .filter(o -> o.status() == orderStatus)
                .toList();
    }

    public Order shipOrder(String id){
        Order order = orderRepo.findById(id)
                .orElseThrow(OrderNotFoundException::new);
        Order shippedOrder = order.ship();
        orderRepo.update(shippedOrder);

        return shippedOrder;
    }

    public Order completeOrder(String id){
        Order order = orderRepo.findById(id)
                .orElseThrow(OrderNotFoundException::new);
        Order completedOrder = order.complete();
        orderRepo.update(completedOrder);

        return completedOrder;
    }
}
