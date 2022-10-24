package com.wardasiu.project.amigoscode.controllers;

import com.wardasiu.project.amigoscode.entities.Customer;
import com.wardasiu.project.amigoscode.repositories.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable(value = "id") long id) {
        Optional<Customer> customer = customerRepository.findCustomersByIdCustomer(id);

        return customer.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Customer saveCustomer(@Validated @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
}
