package com.wardasiu.project.wardasiu.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.wardasiu.project.wardasiu.entities.Cart;
import com.wardasiu.project.wardasiu.entities.Cart;
import com.wardasiu.project.wardasiu.entities.CartItem;
import com.wardasiu.project.wardasiu.entities.Product;
import com.wardasiu.project.wardasiu.entities.User;
import com.wardasiu.project.wardasiu.repositories.CartRepository;
import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import com.wardasiu.project.wardasiu.security.UserService;
import com.wardasiu.project.wardasiu.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductsRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/addItem")
    public ResponseEntity<String> addItemToCart(HttpSession session, Authentication authentication, @RequestBody Map<String, Object> requestBody) {
        Long productId = ((Number) requestBody.get("productId")).longValue();

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

        return ResponseEntity.ok("Item added to cart successfully.");
    }

    @GetMapping("/X")
    public ModelAndView returnCartPage() {

        return new ModelAndView("cart");
    }

    @GetMapping
    public ModelAndView showCart(@RequestAttribute HttpServletRequest request) {
        // Retrieve cart data from local storage
        String cartDataString = request.getParameter("cart");
        if (cartDataString == null) {
            cartDataString = "{}"; // Default empty cart
        }
        Map<String, Integer> cartData = new Gson().fromJson(cartDataString, new TypeToken<Map<String, Integer>>() {}.getType());

        // Retrieve product data for each product in the cart
        List<Optional<Product>> products = new ArrayList<>();
        for (String productId : cartData.keySet()) {
            Optional<Product> product = productRepository.findProductByIdProducts(Long.valueOf(productId));
            if (product != null) {
                products.add(product);
            }
        }

        // Add the products to the model for display in the view
        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("products", products);

        return modelAndView;
    }

}
