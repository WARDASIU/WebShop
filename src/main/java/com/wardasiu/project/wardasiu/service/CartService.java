package com.wardasiu.project.wardasiu.service;

import com.wardasiu.project.wardasiu.entities.Cart;
import com.wardasiu.project.wardasiu.entities.CartItem;
import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.repositories.CartItemRepository;
import com.wardasiu.project.wardasiu.repositories.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
            cart.setUser(user.getIdUser());
            cartRepository.save(cart);

            cartItem.setCart(cart.getIdCart());
            cartItem.setProduct(productId);
            cartItem.setQuantity(1);
            cartItemRepository.save(cartItem);

            cartRepository.save(cart);
            cartItemRepository.save(cartItem);
        }
    }

    public Optional<Cart> findCartByUser(final User user) {
        return cartRepository.findByUser(user.getIdUser());
    }

    public List<CartItem> findCartItemsByUserCart(User user) {
        return cartItemRepository.findAllByCart(cartRepository.findByUser(user.getIdUser()).get().getIdCart());
    }

    public Map<String, Integer> getCartItems(User user){
        if(findCartByUser(user).isEmpty()){
            return Collections.emptyMap();
        }

        List<CartItem> cartItems = cartItemRepository.findAllByCart(cartRepository.findByUser(user.getIdUser()).get().getIdCart());
        Map<String, Integer> cartItemsMap = new HashMap<>();

        for (CartItem cartItem : cartItems) {
            String product = String.valueOf(cartItem.getProduct());
            Integer quantity = cartItem.getQuantity();
            cartItemsMap.put(product, quantity);
        }

        return cartItemsMap;
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