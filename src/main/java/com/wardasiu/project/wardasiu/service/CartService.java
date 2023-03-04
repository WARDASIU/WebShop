package com.wardasiu.project.wardasiu.service;

import com.wardasiu.project.wardasiu.entities.Cart;
import com.wardasiu.project.wardasiu.entities.CartItem;
import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.repositories.CartItemRepository;
import com.wardasiu.project.wardasiu.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

//    public void addItem(CartItem cartItem) {
//        boolean itemExists = false;
//        for (CartItem item : cartItems) {
//            if (item.getProduct().equals(cartItem.getProduct())) {
//                item.setQuantity(item.getQuantity() + 1);
//                itemExists = true;
//                break;
//            }
//        }
//        if (!itemExists) {
//            cartItem.setCart(this.getIdCart());
//            cartItems.add(cartItem);
//        }
//    }
}