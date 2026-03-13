package org.example.service;

import java.util.UUID;
import java.util.function.Supplier;

public class IdService {

    private static final String PRODCT_PREFIX = "prd-";
    private static final String ORDER_PREFIX = "ord-";

    private final Supplier<UUID> uuidSupplier;

    public IdService(Supplier<UUID> uuidSupplier) {
        this.uuidSupplier = uuidSupplier;
    }

    public IdService() {
        this(UUID::randomUUID);
    }

    public String newProductId(){
        return PRODCT_PREFIX + uuidSupplier.get();
    }

    public String newOrderId(){
        return ORDER_PREFIX + uuidSupplier.get();
    }
}
