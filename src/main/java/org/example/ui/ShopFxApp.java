package org.example.ui;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.domain.Order;
import org.example.domain.OrderItem;
import org.example.domain.Product;
import org.example.repository.ProductRepo;
import org.example.service.ShopService;

import java.util.List;

public class ShopFxApp extends Application {

    private static ProductRepo productRepo;
    private static ShopService shopService;

    private final TableView<Product> productTable = new TableView<>();
    private final TextArea outputArea = new TextArea();

    public static void startApp(ProductRepo repo, ShopService service, String[] args) {
        productRepo = repo;
        shopService = service;
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("Mini Shop");

        setupProductTable();

        Button orderButton = new Button("Ausgewählte Produkte bestellen");
        orderButton.setOnAction(e -> placeOrder());

        outputArea.setEditable(false);
        outputArea.setPrefRowCount(10);

        VBox root = new VBox(10,
                titleLabel,
                new Label("Produkte"),
                productTable,
                orderButton,
                new Label("Ergebnis"),
                outputArea
        );

        root.setPadding(new Insets(16));

        refreshProducts();

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Shop System");
        stage.setScene(scene);
        stage.show();
    }

    private void setupProductTable() {
        TableColumn<Product, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().id()));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().name()));

        TableColumn<Product, String> priceColumn = new TableColumn<>("Preis");
        priceColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().price().toString() + " €"));

        TableColumn<Product, String> stockColumn = new TableColumn<>("Bestand");
        stockColumn.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().availablitity())));

        productTable.getColumns().addAll(idColumn, nameColumn, priceColumn, stockColumn);
        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void refreshProducts() {
        productTable.getItems().setAll(productRepo.findAll());
    }

    private void placeOrder() {
        try {
            List<Product> selectedProducts = productTable.getSelectionModel().getSelectedItems();

            if (selectedProducts.isEmpty()) {
                outputArea.setText("Bitte mindestens ein Produkt auswählen.");
                return;
            }

            String[] ids = selectedProducts.stream()
                    .map(Product::id)
                    .toArray(String[]::new);

            Order order = shopService.newOrder(ids);

            StringBuilder sb = new StringBuilder();
            sb.append("Bestellung erfolgreich erstellt\n\n");
            sb.append("Bestell-ID: ").append(order.id()).append("\n");
            sb.append("Status: ").append(order.status()).append("\n\n");
            sb.append("Artikel:\n");

            for (OrderItem item : order.items()) {
                sb.append(String.format(
                        "- %s x%d = %s €%n",
                        item.product().name(),
                        item.quantity(),
                        item.subtotal()
                ));
            }

            sb.append("\nGesamt: ").append(order.total()).append(" €");

            outputArea.setText(sb.toString());

            productTable.getSelectionModel().clearSelection();
            refreshProducts();

        } catch (Exception e) {
            outputArea.setText("Fehler: " + e.getMessage());
        }
    }
}