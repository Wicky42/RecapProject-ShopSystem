package org.example;

import org.example.entity.Product;
import org.example.repository.OrderMapRepo;
import org.example.repository.OrderRepoInterface;
import org.example.repository.ProductRepo;
import org.example.service.ShopService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {

        Product p1 = new Product(2039, "Topfpflanze", BigDecimal.valueOf(3.99));
        Product p2 = new Product(2384, "Adventskalender", BigDecimal.valueOf(14.99));
        Product p3 = new Product(1891, "Pizza", BigDecimal.valueOf(4.59));
        Product p4 = new Product(3697, "Erdbeeren", BigDecimal.valueOf(3.95));

        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);

        ProductRepo prodRepo = new ProductRepo(products);
        OrderRepoInterface orderRepo = new OrderMapRepo();

        ShopService shopService = new ShopService(prodRepo, orderRepo);

        shopService.newOrder(2039, 3697);
        System.out.println(orderRepo.findAll());


    }
}
