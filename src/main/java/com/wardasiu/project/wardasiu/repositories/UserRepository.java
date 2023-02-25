package com.wardasiu.project.wardasiu.repositories;

import com.wardasiu.project.wardasiu.entities.Users;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<Users, Long>{
    List<Users> findAll();

    Users findByUsername(String username);
}
