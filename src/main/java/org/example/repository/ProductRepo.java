package org.example.repository;

import org.example.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepo {

    private final List<Product> products;

    public ProductRepo() {
        products = new ArrayList<>();
    }

    public ProductRepo(List<Product> products) {
        this.products = products;
    }

    public void add(Product product){
        products.add(product);
    }
    public void deleteById(String id){
        products.removeIf(p ->p.id().equals(id));
    }

    public Optional<Product> findById(String id){
        return products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst();
    }

    public List<Product> findAll(){
        return List.copyOf(products);
    }

    public void update(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).id().equals( updatedProduct.id())) {
                products.set(i, updatedProduct);
                return;
            }
        }
        throw new IllegalArgumentException("Product not found: " + updatedProduct.id());
    }
}
