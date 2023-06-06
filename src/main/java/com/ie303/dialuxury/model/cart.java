package com.ie303.dialuxury.model;

import org.springframework.data.mongodb.core.mapping.Document;

public class cart {
    private product product;
    private long totalPrice;
    private int quantity;

    public cart() {
    }

    public product getProduct() {
        return product;
    }

    public void setProduct(product product) {
        this.product = product;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
