package com.wardasiu.project.wardasiu.controllers;


import com.wardasiu.project.wardasiu.entities.Cart;
import com.wardasiu.project.wardasiu.entities.CartItem;
import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.security.UserService;
import com.wardasiu.project.wardasiu.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/addItem")
    public ResponseEntity<String> addItemToCart(HttpSession session, Authentication authentication, @RequestBody Map<String, Object> requestBody) {
        String stringProductId = String.valueOf(requestBody.get("productId"));
        Long productId = Long.parseLong(stringProductId);

        if (authentication != null) {
            User user = userService.findUserByUsername(authentication.getName());
            cartService.addItem(user, productId);
        } else {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
        }

        return ResponseEntity.ok("Item added to cart successfully");
    }

    @PostMapping("/removeItem")
    public ResponseEntity<String> removeItemFromCart(Authentication authentication, @RequestBody Map<String, Object> requestBody) {
        String stringProductId = String.valueOf(requestBody.get("productId"));
        Long productId = Long.parseLong(stringProductId);

        User user = userService.findUserByUsername(authentication.getName());
        cartService.removeItem(user, productId);

        return ResponseEntity.ok("Item added to cart successfully");
    }

    @GetMapping("/getCart")
    public Optional<Cart> getUserCart(Authentication authentication) {
        User user;
        if (authentication != null) {
            user = userService.findUserByUsername(authentication.getName());
        } else return Optional.empty();

        return cartService.findCartByUser(user);
    }

    @GetMapping("/getItemsFromUserCart")
    public List<CartItem> getItemsFromUserCart(Authentication authentication) {
        User user;
        if (authentication != null) {
            user = userService.findUserByUsername(authentication.getName());
        } else return Collections.emptyList();

        return cartService.findCartItemsByUserCart(user);
    }

    @GetMapping("/getItemsFromUserCartSortedData")
    public Map<String, Integer> getItemsFromUserCartSortedData(Authentication authentication) {
        User user;
        if (authentication != null) {
            user = userService.findUserByUsername(authentication.getName());
        } else return null;

        return cartService.getCartItems(user);
    }
}