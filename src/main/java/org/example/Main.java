package org.example;

import org.example.domain.Order;
import org.example.domain.Product;
import org.example.io.ProductCsvLoader;
import org.example.repository.OrderListRepo;
import org.example.repository.OrderMapRepo;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepo;
import org.example.service.IdService;
import org.example.service.ShopService;
import org.example.ui.ShopFxApp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final ProductRepo productRepo;
    private final ShopService shopService;
    private final Scanner scanner;

    public Main(ProductRepo productRepo, ShopService shopService) {
        this.productRepo = productRepo;
        this.shopService = shopService;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        showCatalog();

        try {
            List<String> productIds = readProductIds();
            Order order = createOrder(productIds);
            printSuccessMessage(order);
            System.out.println("Weitere Bestellung aufgeben?  Y: ja: N : beenden");
            String tmp = scanner.nextLine();
            if(tmp.equals("Y")){
                run();
            }
        } catch (IllegalArgumentException e) {
            printErrorMessage();
        }
    }

    private void showCatalog() {
        System.out.println("Willkommen in unserem Shop!");
        System.out.println("Verfügbare Produkte:");
        System.out.println();

        for (Product product : productRepo.findAll()) {
            if(product.availablitity() != 0) {
                System.out.printf("ID: %s | %-35s | %6.2f € | %d %n",
                        product.id(),
                        product.name(),
                        product.price(),
                        product.availablitity());
            }
        }

        System.out.println();
        System.out.println("Bitte geben Sie die Produkt-IDs ein, getrennt durch Komma:");
    }

    private List<String> readProductIds() {
        String input = scanner.nextLine();

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .toList();
    }

    private Order createOrder(List<String> productIds) {
        return shopService.newOrder(productIds.toArray(String[]::new));
    }

    private void printSuccessMessage(Order order) {
        System.out.println();
        System.out.println("Ihre Bestellung wurde erfolgreich erstellt.");
        System.out.println("Hier ist eine Übersicht der bestellten Artikel:");

        order.items()
                .forEach(item ->
                        System.out.printf("- %s (%s) x%d: %.2f €%n",
                                item.product().name(),
                                item.product().id(),
                                item.quantity(),
                                item.product().price())
                );

        System.out.println("----------------------------------------");
        System.out.printf("Gesamtpreis: %.2f €%n", order.total());
        System.out.printf("Zeitpunkt Ihrer Bestellung: " + order.timeStamp() + "%n" );

    }

    private void printErrorMessage() {
        System.out.println();
        System.out.println("Das Produkt / die Produkte konnten nicht gefunden werden.");
        System.out.println("Bitte versuchen Sie es erneut.");
    }

    static void main(String[] args) throws IOException {
//        ProductCsvLoader loader = new ProductCsvLoader();
//
//        ProductRepo productRepo = new ProductRepo();
//        loader.load("productCatalog.csv").forEach(productRepo::add);
//
//        OrderListRepo orderRepo = new OrderListRepo();
//        IdService idService = new IdService();
//        ShopService shopService = new ShopService(productRepo, orderRepo, idService);
//
//        Main app = new Main(productRepo, shopService);
//        app.run();

            ProductRepo productRepo = new ProductRepo();
            OrderRepository orderRepo = new OrderMapRepo();
            IdService idService = new IdService();
            ShopService shopService = new ShopService(productRepo, orderRepo, idService);

            // Demo-Produkte anlegen ...

            ShopFxApp.startApp(productRepo, shopService, args);


    }
}