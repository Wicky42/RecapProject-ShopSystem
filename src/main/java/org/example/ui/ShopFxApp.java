package org.example.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.domain.Order;
import org.example.domain.OrderItem;
import org.example.domain.Product;
import org.example.repository.ProductRepo;
import org.example.service.ShopService;

public class ShopFxApp extends Application {

    private static ProductRepo productRepo;
    private static ShopService shopService;

    private final TextArea catalogArea = new TextArea();
    private final TextField inputField = new TextField();
    private final TextArea outputArea = new TextArea();

    public static void startApp(ProductRepo repo, ShopService service, String[] args) {
        productRepo = repo;
        shopService = service;
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("Mini Shop");

        catalogArea.setEditable(false);
        catalogArea.setPrefRowCount(15);

        inputField.setPromptText("Produkt-IDs eingeben, z. B. prd-123, prd-456, prd-456");

        Button orderButton = new Button("Bestellung aufgeben");
        orderButton.setOnAction(e -> placeOrder());

        outputArea.setEditable(false);
        outputArea.setPrefRowCount(10);

        VBox root = new VBox(10,
                titleLabel,
                new Label("Produkte"),
                catalogArea,
                new Label("Produkt-IDs (kommagetrennt)"),
                inputField,
                orderButton,
                new Label("Ergebnis"),
                outputArea
        );

        root.setPadding(new Insets(16));

        refreshCatalog();

        Scene scene = new Scene(root, 700, 650);
        stage.setTitle("Shop System");
        stage.setScene(scene);
        stage.show();
    }

    private void refreshCatalog() {
        StringBuilder sb = new StringBuilder();
        sb.append("Verfügbare Produkte:\n\n");

        for (Product product : productRepo.findAll()) {
            sb.append(String.format(
                    "%s | %s | %.2f € | Bestand: %d%n",
                    product.id(),
                    product.name(),
                    product.price(),
                    product.availablitity()   // falls es bei dir noch availablitity() heißt, hier anpassen
            ));
        }

        catalogArea.setText(sb.toString());
    }

    private void placeOrder() {
        try {
            String input = inputField.getText().trim();

            if (input.isBlank()) {
                outputArea.setText("Bitte mindestens eine Produkt-ID eingeben.");
                return;
            }

            String[] ids = input.split(",");
            for (int i = 0; i < ids.length; i++) {
                ids[i] = ids[i].trim();
            }

            Order order = shopService.newOrder(ids);

            StringBuilder sb = new StringBuilder();
            sb.append("Bestellung erfolgreich erstellt\n\n");
            sb.append("Bestell-ID: ").append(order.id()).append("\n");
            sb.append("Status: ").append(order.status()).append("\n\n");
            sb.append("Artikel:\n");

            for (OrderItem item : order.items()) {
                sb.append(String.format(
                        "- %s x%d = %.2f €%n",
                        item.product().name(),
                        item.quantity(),
                        item.subtotal()
                ));
            }

            sb.append(String.format("%nGesamt: %.2f €", order.total()));

            outputArea.setText(sb.toString());
            inputField.clear();
            refreshCatalog();

        } catch (Exception e) {
            outputArea.setText("Fehler: " + e.getMessage());
        }
    }
}