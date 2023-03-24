package com.wardasiu.project.wardasiu.repositories;

import com.wardasiu.project.wardasiu.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ProductsRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findProductByIdProducts(@Param("id") Long id);
    Optional<Product> findProductByName(@Param("name") String name);

    Optional<Product> findProductByNameContains(@Param("name") String name);

    void deleteProductByIdProducts(long id);

    @Override
    void delete(Product entity);

    @Override
    <S extends Product> S save(S entity);
}
