package com.wardasiu.project.wardasiu.dialogflow.controller;

import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ProductAvailabilityController {
    @Autowired
    private ProductsRepository productsRepository;

    @PostMapping(value = "/webhook")
    public ResponseEntity<Object> checkProductAvailability(@RequestBody Map<String, Object> payload) {
        Map<String, Object> queryResult = (Map<String, Object>) payload.get("queryResult");
        Map<String, Object> parameters = (Map<String, Object>) queryResult.get("parameters");
        String productNameParameter = (String) parameters.get("product");

        Map<String, Object> response = new HashMap<>();
        log.info("queryResult: " + payload.get("queryResult"));
        log.info("Produkt: " + productNameParameter);

        if (productsRepository.findProductByNameContains(productNameParameter).isPresent()){
            response.put("fulfillmentText", "Produkt " + productNameParameter + " jest dostępny!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("fulfillmentText", "Produkt " + productNameParameter + " nie istnieje, bądź nie jest dostępny!");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}