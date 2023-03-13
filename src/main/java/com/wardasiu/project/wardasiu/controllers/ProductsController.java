package com.wardasiu.project.wardasiu.controllers;

import com.google.gson.Gson;
import com.wardasiu.project.wardasiu.entities.Product;
import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
public class ProductsController {
    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsController(final ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping("/product/{productId}")
    public ModelAndView getDetailedProductView(@PathVariable Long productId) {
        // Get product data from database using the productId parameter
        Optional<Product> product = productsRepository.findProductByIdProducts(productId);

        // Create a new ModelAndView object with the "product" view name
        ModelAndView modelAndView = new ModelAndView("detailed-product-view");

        // Add the product data to the ModelAndView object as a model attribute
        modelAndView.addObject("detailed-product-view", product);

        // Return the ModelAndView object
        return modelAndView;
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
    public ResponseEntity<?> addProduct(String name, String description,
                                  int price, int in_stock) {
        long nextId = productsRepository.findAll().stream().mapToLong(Product::getIdProducts).max().orElse(0) + 1;
        productsRepository.save(new Product(nextId, name, description, price, in_stock));

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") long id,
                                                 @RequestBody Map<String, Object> productDetails) throws IllegalAccessException {
        Optional<Product> product = productsRepository.findById(id);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            for (Map.Entry<String, Object> entry : productDetails.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                Field field = ReflectionUtils.findField(Product.class, fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    if (field.getType() == int.class) {
                        field.set(existingProduct, Integer.parseInt((String) fieldValue));
                    } else {
                        field.set(existingProduct, fieldValue);
                    }
                }
            }
            Product updatedProduct = productsRepository.save(existingProduct);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productsRepository.findProductByIdProducts(id);
        optionalProduct.ifPresent(productsRepository::delete);

        return ResponseEntity.ok().build();
    }
}
