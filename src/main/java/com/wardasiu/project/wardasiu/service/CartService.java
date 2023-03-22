package com.wardasiu.project.wardasiu.service;

import com.wardasiu.project.wardasiu.entities.Cart;
import com.wardasiu.project.wardasiu.entities.CartItem;
import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.repositories.CartItemRepository;
import com.wardasiu.project.wardasiu.repositories.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public void addItem(User user, Long productId) {
        Optional<Cart> cartOptional = cartRepository.findByUser(user.getIdUser());

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndProduct(cart.getIdCart(), productId);

            CartItem cartItem;
            if (optionalCartItem.isPresent()) {
                cartItem = optionalCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            } else {
                cartItem = new CartItem();
                cartItem.setCart(cart.getIdCart());
                cartItem.setProduct(productId);
                cartItem.setQuantity(1);
            }
            cartItemRepository.save(cartItem);
        } else {
            Cart cart = new Cart();
            CartItem cartItem = new CartItem();

            cartItem.setCart(cart.getIdCart());
            cartItem.setProduct(productId);
            cartItem.setQuantity(1);

            cartItemRepository.save(cartItem);
            cart.setUser(user.getIdUser());
            cart.setIdCartItem(cartItem.getId_cart_item());
            cartRepository.save(cart);
        }
    }

    public Optional<Cart> findByUser(final User user) {
        log.info(cartRepository.findByUser(user.getIdUser()).get().toString());
        return cartRepository.findByUser(user.getIdUser());
    }

    public void removeItem(User user, Long productId) {
        Optional<Cart> cartOptional = cartRepository.findByUser(user.getIdUser());

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndProduct(cart.getIdCart(), productId);

            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    cartItemRepository.save(cartItem);
                } else {
                    cartItemRepository.delete(cartItem);
                }
            } else {
                log.warn("Product not found in cart");
            }
        } else {
            log.warn("Cart not found for user");
        }
    }
}