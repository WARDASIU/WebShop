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
import java.util.List;
import java.util.Optional;

@RestController
public class ProductsController {
    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsController(final ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findProductsById(@PathVariable(value = "id") long id) {
        Optional<Product> product = productsRepository.findProductByIdProducts(id);

        return product.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/products/images/{filename}")
    @ResponseBody
    public byte[] getImage(@PathVariable String filename) throws IOException {
        String filePath = Paths.get("src/main/resources/img", filename).toString();
        return Files.readAllBytes(Paths.get(filePath));
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String getProductsAsList() {
        Gson gson = new Gson();

        return gson.toJson(productsRepository.findAll());
    }
}
