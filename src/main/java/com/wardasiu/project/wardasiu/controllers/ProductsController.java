package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.entities.Product;
import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
class ProductsController {
    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsController(final ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping
    public List<Product> findAllProducts() {
        return productsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductsById(@PathVariable(value = "id") long id) {
        Optional<Product> product = productsRepository.findProductByIdProducts(id);

        return product.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product saveCustomer(@Validated @RequestBody Product product) {
        return productsRepository.save(product);
    }
}
