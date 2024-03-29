package com.wardasiu.project.wardasiu.repositories;

import com.wardasiu.project.wardasiu.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long>{
    List<User> findAll();
    List<User> findAllByNewsletterTrue();

    User findByEmail(String email);
    User findByUsername(String username);

    @Override
    <S extends User> S save(S entity);

    @Override
    void deleteById(Long aLong);
}
