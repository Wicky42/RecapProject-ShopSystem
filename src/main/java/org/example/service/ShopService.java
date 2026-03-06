package org.example.service;

import org.example.entity.Order;
import org.example.entity.Product;
import org.example.repository.OrderRepoInterface;
import org.example.repository.ProductRepo;

import java.util.ArrayList;
import java.util.List;

public class ShopService {

    private final ProductRepo productRepo;
    private final OrderRepoInterface orderRepo;

    private static int nextOrderId = 12345;

    public ShopService(ProductRepo productRepo, OrderRepoInterface orderListRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderListRepo;
    }

    public Order newOrder(Integer... ids) {
        List<Product> products = new ArrayList<>();

        for (Integer id : ids) {
            Product product = productRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
            products.add(product);
        }

        for (Product product : products) {
            Product updatedProduct = product.sell(1);
            productRepo.update(updatedProduct);
        }

        Order newOrder = new Order(nextOrderId++, products);
        orderRepo.add(newOrder);
        return newOrder;
    }

}
