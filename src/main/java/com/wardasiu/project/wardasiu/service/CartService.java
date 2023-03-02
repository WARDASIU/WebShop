package com.wardasiu.project.wardasiu.service;

import com.wardasiu.project.wardasiu.entities.Cart;
import com.wardasiu.project.wardasiu.entities.CartItem;
import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.repositories.CartItemRepository;
import com.wardasiu.project.wardasiu.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public void addItem(User user, Long productId) {
        Cart cart = cartRepository.findByUser(user.getIdUser());

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user.getIdUser());
            cartRepository.save(cart);
        }
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart.getIdCart(), productId);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart.getIdCart());
            cartItem.setProduct(productId);
            cartItem.setQuantity(1);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        cartItemRepository.save(cartItem);
    }
}