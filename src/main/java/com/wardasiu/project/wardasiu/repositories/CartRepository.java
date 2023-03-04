package com.wardasiu.project.wardasiu.repositories;

import com.wardasiu.project.wardasiu.entities.Cart;
import com.wardasiu.project.wardasiu.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(Long id_user);

    @Override
    <S extends Cart> S save(S entity);
}
