package com.wardasiu.project.wardasiu.repositories;

import com.wardasiu.project.wardasiu.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByIdOrder(final Long idOrder);

    @Override
    <S extends OrderItem> S save(S entity);
}
