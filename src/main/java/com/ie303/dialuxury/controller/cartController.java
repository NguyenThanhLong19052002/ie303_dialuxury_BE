package com.ie303.dialuxury.controller;

import com.ie303.dialuxury.model.cart;

import com.ie303.dialuxury.service.cartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class cartController {
    @Autowired
    private final cartServiceImpl cartService;

    public cartController(cartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public void addToCart(@RequestBody cart cartItem, HttpServletRequest request) {
        cartService.addToCart(cartItem, request);
    }

    @PostMapping("/update")
    public void updateCartItem(@RequestBody cart cartItem, HttpServletRequest request) {
        cartService.updateCartItem(cartItem, request);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public void removeCartItem(@PathVariable String cartItemId, HttpServletRequest request) {
        cartService.removeCartItem(cartItemId, request);
    }

    @GetMapping("")
    public List<cart> getCartItems(HttpServletRequest request) {
        return cartService.getCartItems(request);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }
}
