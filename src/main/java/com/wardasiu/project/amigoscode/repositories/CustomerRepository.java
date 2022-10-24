package com.wardasiu.project.amigoscode.repositories;

import com.wardasiu.project.amigoscode.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAll();
    Optional<Customer> findCustomersByIdCustomer(final Long id);
}
