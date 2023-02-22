package com.wardasiu.project.wardasiu.repositories;

import com.wardasiu.project.wardasiu.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    List<User> findAll();

    User findByUsername(String username);
}
