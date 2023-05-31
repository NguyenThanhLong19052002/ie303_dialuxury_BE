package com.ie303.dialuxury.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class cart {
    private product product;
    private double totalPrice;
    private int quantity;

    public cart() {
    }

    public com.ie303.dialuxury.model.product getProduct() {
        return product;
    }

    public void setProduct(com.ie303.dialuxury.model.product product) {
        this.product = product;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
