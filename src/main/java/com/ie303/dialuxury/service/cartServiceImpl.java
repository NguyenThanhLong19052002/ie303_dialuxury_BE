package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.cart;
import com.ie303.dialuxury.repository.cartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class cartServiceImpl implements cartService {
    private static final String CART_SESSION_ATTRIBUTE = "cart";

    @Autowired
    private cartRepository cartItemRepository;

    @Autowired
    private HttpSession httpSession;

    @Override
    public void addToCart(cart cartItem) {
        List<cart> cart = getCartItemsFromSession();
        cart.add(cartItem);
        saveCartToSession(cart);
    }

    @Override
    public void updateCartItem(cart cartItem) {
        List<cart> cart = getCartItemsFromSession();
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getproductid().equals(cartItem.getProduct().getproductid())) {
                cart.set(i, cartItem);
                break;
            }
        }
        saveCartToSession(cart);
    }

    @Override
    public void removeCartItem(String cartItemId) {
        List<cart> cart = getCartItemsFromSession();
        cart.removeIf(item -> item.getProduct().getproductid().equals(cartItemId));
        saveCartToSession(cart);
    }

    @Override
    public List<cart> getCartItems() {
        return getCartItemsFromSession();
    }

    @Override
    public void clearCart() {
        saveCartToSession(new ArrayList<>());
    }

    private List<cart> getCartItemsFromSession() {
        List<cart> cart = (List<cart>) httpSession.getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            cart = new ArrayList<>();
        }
        return cart;
    }

    private void saveCartToSession(List<cart> cart) {
        httpSession.setAttribute(CART_SESSION_ATTRIBUTE, cart);
    }

}
