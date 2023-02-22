package com.wardasiu.project.wardasiu.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wardasiu.project.wardasiu.entities.Product;
import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import com.wardasiu.project.wardasiu.service.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
public class CartController {
    @Autowired
    private ProductsRepository productRepository;
    private final Cart cart;

    public CartController() {
        this.cart = new Cart();
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestBody Map<String, Long> requestBody) {
        Long productId = requestBody.get("productId");
        Optional<Product> product = productRepository.findProductByIdProducts(productId);
        log.info(productId.toString());
        cart.addProductByProduct(product);
    }

    @PostMapping("/cart/addItem")
    public void addItemToCart(HttpSession session, @RequestBody Map<String, Long> requestBody) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        Long productId = requestBody.get("productId");
        Optional<Product> product = productRepository.findProductByIdProducts(productId);
        cart.addProductByProduct(product);
    }

    @RequestMapping("/cartX")
    public ModelAndView returnCartPage() {

        return new ModelAndView("cart");
    }

    @GetMapping("/cart")
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
