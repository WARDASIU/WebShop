package com.wardasiu.project.wardasiu.service;

import com.wardasiu.project.wardasiu.entities.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Cart {
    private List<Optional<Product>> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProductByProduct(Optional<Product> product) {
        if (product.isPresent()) {
            products.add(product);
        } else {

        }
    }
}
