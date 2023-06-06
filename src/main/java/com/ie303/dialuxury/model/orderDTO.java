package com.ie303.dialuxury.model;

import java.util.List;

public class orderDTO {
    private List<cart> cart;
    private String image;
    private String shippingAddress;
    private String paymentMethod;
    private long total;

    public orderDTO() {
    }

    public List<cart> getCart() {
        return cart;
    }

    public void setCart(List<cart> cart) {
        this.cart = cart;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
