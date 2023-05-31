package com.ie303.dialuxury.controller;

import com.ie303.dialuxury.model.cart;
import com.ie303.dialuxury.service.cartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class cartController {
    @Autowired
    private cartService cartService;

    @PostMapping("/add")
    public void addToCart(@RequestBody cart cartItem) {
        cartService.addToCart(cartItem);
    }

    @PostMapping("/update")
    public void updateCartItem(@RequestBody cart cartItem) {
        cartService.updateCartItem(cartItem);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public void removeCartItem(@PathVariable String cartItemId) {
        cartService.removeCartItem(cartItemId);
    }

    @GetMapping
    public List<cart> getCartItems() {
        return cartService.getCartItems();
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }
}
