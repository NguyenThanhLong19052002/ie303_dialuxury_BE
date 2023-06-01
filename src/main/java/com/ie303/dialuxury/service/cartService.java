package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.cart;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface cartService {
    void addToCart(cart cartItem, HttpServletRequest request);
    void updateCartItem(cart cartItem, HttpServletRequest request);
    void removeCartItem(String cartItemId, HttpServletRequest request);
    List<cart> getCartItems(HttpServletRequest request);
    void clearCart();
}
