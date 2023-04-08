package com.wardasiu.project.wardasiu.service;

import com.wardasiu.project.wardasiu.entities.CartItem;
import com.wardasiu.project.wardasiu.entities.Order;
import com.wardasiu.project.wardasiu.entities.OrderItem;
import com.wardasiu.project.wardasiu.entities.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    Optional<Order> getOrder(long orderId);

    List<Order> getOrders(long userId);

    Order createOrder(Order order);

    File generateInvoiceForOrder(User user, List<CartItem> cartItems, Order order);

    File generateInvoiceForUnauthorizedOrder(Map<String, String> values, Map<String, Integer> sessionStorageItems, Order order);
}