package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.entities.Product;
import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductsController {
    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsController(final ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    @GetMapping
    @RequestMapping("/api/products")
    public List<Product> findAllProducts() {
        return productsRepository.findAll();
    }

    @GetMapping()
    @RequestMapping("/product/{id}")
    public ResponseEntity<Product> findProductsById(@PathVariable(value = "id") long id) {
        Optional<Product> product = productsRepository.findProductByIdProducts(id);

        return product.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/products")
    public ModelAndView getProductsAsList() {
        ModelAndView mav = new ModelAndView("products");
        mav.addObject("products", productsRepository.findAll());

        return mav;
    }
}
