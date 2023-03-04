package com.wardasiu.project.wardasiu.controllers;

import com.google.gson.Gson;
import com.wardasiu.project.wardasiu.entities.Product;
import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
public class ProductsController {
    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsController(final ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<Product> findProductByName(@PathVariable(value = "name") String name) {
        Optional<Product> product = productsRepository.findProductByName(name);

        return product.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{Id}")
    public ResponseEntity<Product> findProductById(@PathVariable(value = "Id") long id) {
        Optional<Product> product = productsRepository.findProductByIdProducts(id);

        return product.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        Optional<Product> product = productsRepository.findProductByIdProducts(productId);

        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



    @GetMapping("/product/{Id}/images/{filename}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "Id") long id, @PathVariable String filename) throws IOException {
        Optional<Product> product = productsRepository.findProductByIdProducts(id);
        String productName = product.get().getName().replace(" ", "_");
        String path = "src/main/resources/img/" + productName + "/";
        String filePath = Paths.get(path, filename + ".png").toString();

        return Files.readAllBytes(Paths.get(filePath));
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ModelAndView getProductsPage() {
        return new ModelAndView("products");
    }

    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public String getProductsAsList() {
        Gson gson = new Gson();

        return gson.toJson(productsRepository.findAll());
    }

    @PostMapping("/admin")
    public ModelAndView addProduct(String name, String description,
                                  int price, int in_stock) {
        long nextId = productsRepository.findAll().stream().mapToLong(Product::getIdProducts).max().orElse(0) + 1;
        productsRepository.save(new Product(nextId, name, description, price, in_stock));

        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("productAdded", "Product added!");

        return modelAndView;
    }
}
