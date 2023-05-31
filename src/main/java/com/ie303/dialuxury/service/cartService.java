package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.cart;

import java.util.List;

public interface cartService {
    void addToCart(cart cartItem);
    void updateCartItem(cart cartItem);
    void removeCartItem(String cartItemId);
    List<cart> getCartItems();
    void clearCart();
}
