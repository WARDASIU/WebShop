package com.wardasiu.project.wardasiu.service;

import com.google.common.io.Resources;
import com.wardasiu.project.wardasiu.entities.*;
import com.wardasiu.project.wardasiu.repositories.OrderItemRepository;
import com.wardasiu.project.wardasiu.repositories.OrderRepository;
import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import com.wardasiu.project.wardasiu.service.invoice.InvoiceReceiver;
import com.wardasiu.project.wardasiu.service.invoice.InvoiceRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private InvoiceGenerator invoiceGenerator;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public Optional<Order> getOrder(long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> getOrders(long userId) {
        return orderRepository.findByIdUser(userId);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public File generateInvoiceForOrder(User user, List<CartItem> cartItems, Order order) {
        InvoiceReceiver invoiceReceiver = InvoiceReceiver.builder()
                .email(user.getEmail())
                .phone(String.valueOf(user.getPhone()))
                .address(user.getAddress())
                .name(user.getName() + " " + user.getSurname())
                .build();

        List<OrderItem> orderItems = createOrderItemsForOrder(cartItems, order);

        List<InvoiceRow> invoiceRows = new ArrayList<>();

        for (OrderItem item : orderItems) {
            InvoiceRow row = InvoiceRow.builder()
                    .name(item.getName())
                    .quantity(item.getQuantity())
                    .price(BigDecimal.valueOf(item.getPrice()))
                    .build();
            invoiceRows.add(row);
        }

        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("invoice.pdf")).getPath();
        file = file.substring(1);

        return invoiceGenerator.generateInvoice(invoiceReceiver, invoiceRows, file);
    }

    private List<OrderItem> createOrderItemsForOrder(List<CartItem> cartItems, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Optional<Product> product = productsRepository.findProductByIdProducts(cartItem.getProduct());

            OrderItem orderItem = new OrderItem();
            orderItem.setName(product.get().getName());
            orderItem.setPrice(product.get().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setIdOrder(order.getIdOrder());
            orderItems.add(orderItem);
            orderItemRepository.save(orderItem);
        }

        return orderItems;
    }
}