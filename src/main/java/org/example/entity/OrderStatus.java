package org.example.entity;

public enum OrderStatus {
    PROCESSING {
        @Override
        public OrderStatus ship() {
            return IN_DELIVERY;
        }
    },
    IN_DELIVERY {
        @Override
        public OrderStatus complete() {
            return COMPLETED;
        }
    },
    COMPLETED;

    public OrderStatus ship() {
        throw new IllegalStateException("Cannot ship from status " + this);
    }

    public OrderStatus complete() {
        throw new IllegalStateException("Cannot complete from status " + this);
    }
}