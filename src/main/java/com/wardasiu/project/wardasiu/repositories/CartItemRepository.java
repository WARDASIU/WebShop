package com.wardasiu.project.wardasiu.repositories;

import com.wardasiu.project.wardasiu.entities.Cart;
import com.wardasiu.project.wardasiu.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProduct(Long cartId, Long productId);
    @Override
    <S extends CartItem> S save(S entity);
}
