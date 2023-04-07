package com.wardasiu.project.wardasiu.repositories;

import com.wardasiu.project.wardasiu.entities.Order;
import com.wardasiu.project.wardasiu.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByIdUser(Long userId);

    @Override
    <S extends Order> S save(S entity);

    void save(List<OrderItem> orderItems);
}