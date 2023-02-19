package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.entities.Product;
import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import com.wardasiu.project.wardasiu.service.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        cart.addProductById(product);
    }
}
