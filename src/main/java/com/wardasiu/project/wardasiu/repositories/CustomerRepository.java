package com.wardasiu.project.wardasiu.repositories;

import com.wardasiu.project.wardasiu.entities.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
    List<Customer> findAll();
    Optional<Customer> findCustomerByIdCustomer(@Param("id") Long id);
}
