package com.ie303.dialuxury.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


public class orderAggregate {

    private String _id;
    private String userId;
    private String status;
    private String shippingAddress;
    private String paymentMethod;
    private long totalPrice;
    private int quantity;
    private long totalPriceOrder;
    private product product;
    private String orderDetailId;
    private Date createdAt;

    public orderAggregate() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public long getTotalPriceOrder() {
        return totalPriceOrder;
    }

    public void setTotalPriceOrder(long totalPriceOrder) {
        this.totalPriceOrder = totalPriceOrder;
    }

    public com.ie303.dialuxury.model.product getProduct() {
        return product;
    }

    public void setProduct(com.ie303.dialuxury.model.product product) {
        this.product = product;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
