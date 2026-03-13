package org.example.service;

import org.example.entity.Order;
import org.example.entity.OrderItem;
import org.example.entity.OrderStatus;
import org.example.entity.Product;
import org.example.repository.OrderRepoInterface;
import org.example.repository.ProductRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopService {

    private final ProductRepo productRepo;
    private final OrderRepoInterface orderRepo;

    private static int nextOrderId = 12345;

    public ShopService(ProductRepo productRepo, OrderRepoInterface orderListRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderListRepo;
    }

    public Order newOrder(Integer... ids) {
        Map<Integer, Integer> quantities = new HashMap<>();

        for (Integer id : ids) {
            quantities.merge(id, 1, Integer::sum);
        }

        List<OrderItem> items = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : quantities.entrySet()) {

            Integer id = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

            Product updatedProduct = product.sell(quantity);
            productRepo.update(updatedProduct);

            items.add(new OrderItem(product, quantity));
        }

        Order order = new Order(nextOrderId++, items, OrderStatus.PROCESSING);
        orderRepo.add(order);

        return order;
    }

}
