package com.ie303.dialuxury.service;

import com.ie303.dialuxury.model.cart;
import com.ie303.dialuxury.repository.cartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class cartServiceImpl implements cartService {
    private static final String CART_SESSION_ATTRIBUTE = "cart";

    @Autowired
    private cartRepository cartItemRepository;

    @Autowired
    private HttpSession Session;

    @Override
    public void addToCart(cart cartItem, HttpServletRequest request) {
        List<cart> cart = getCartItemsFromSession(request);

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng hay chưa
        boolean isProductExists = false;

        if(cart != null){
            for (cart item : cart) {
                if (item.getProduct().getproductid().equals(cartItem.getProduct().getproductid())) {
                    // Nếu sản phẩm đã tồn tại, gộp số lượng
                    item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                    isProductExists = true;
                    break;
                }
            }
        }


        // Nếu sản phẩm chưa tồn tại, thêm vào giỏ hàng
        if (!isProductExists) {
            cart.add(cartItem);
        }
        saveCartToSession(cart);
    }

    @Override
    public void updateCartItem(cart cartItem, HttpServletRequest request) {
        List<cart> cart = getCartItemsFromSession(request);
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getproductid().equals(cartItem.getProduct().getproductid())) {
                cart.set(i, cartItem);
                break;
            }
        }
        saveCartToSession(cart);
    }

    @Override
    public void removeCartItem(String cartItemId, HttpServletRequest request) {
        List<cart> cart = getCartItemsFromSession(request);
        cart.removeIf(item -> item.getProduct().getproductid().equals(cartItemId));
        saveCartToSession(cart);
    }

    @Override
    public List<cart> getCartItems(HttpServletRequest request) {
        return getCartItemsFromSession(request);
    }

    @Override
    public void clearCart() {
        saveCartToSession(new ArrayList<>());
    }

    private List<cart> getCartItemsFromSession(HttpServletRequest request) {
        Session = request.getSession();
        List<cart> cart = (List<cart>) Session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            cart = new ArrayList<>();
        }
        return cart;
    }

    private void saveCartToSession(List<cart> cart) {
        Session.setAttribute(CART_SESSION_ATTRIBUTE, cart);
    }

}
