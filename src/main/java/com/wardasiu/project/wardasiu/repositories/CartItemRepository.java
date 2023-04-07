package com.wardasiu.project.wardasiu.repositories;

import com.wardasiu.project.wardasiu.entities.Cart;
import com.wardasiu.project.wardasiu.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Long cartId, Long productId);
    List<CartItem> findAllByCart(final Long cart);

    @Override
    <S extends CartItem> S save(S entity);

    @Override
    void delete(CartItem entity);
}
