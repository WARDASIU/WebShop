package com.wardasiu.project.wardasiu.service;

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
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final static String PATH = "src\\main\\resources\\static\\invoices";
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
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
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
                .postCode(user.getPostCode())
                .name(user.getName() + " " + user.getSurname())
                .build();

        List<OrderItem> orderItems = createOrderItemsForOrderFromCartItems(cartItems, order);

        List<InvoiceRow> invoiceRows = generateInvoiceRows(orderItems);

        return invoiceGenerator.generateInvoice(invoiceReceiver, invoiceRows, PATH, order.getIdOrder().toString());
    }

    @Override
    public File generateInvoiceForUnauthorizedOrder(Map<String, String> values, Map<String, Integer> sessionStorageItems, Order order) {
        InvoiceReceiver invoiceReceiver = InvoiceReceiver.builder()
                .email(values.get("email"))
                .phone(values.get("phone"))
                .address(values.get("address"))
                .postCode(values.get("post_code"))
                .name(values.get("name") + " " + values.get("surname"))
                .build();

        List<OrderItem> orderItems = createOrderItemsForOrderFromSessionStorage(sessionStorageItems, order);
        List<InvoiceRow> invoiceRows = generateInvoiceRows(orderItems);

        return invoiceGenerator.generateInvoice(invoiceReceiver, invoiceRows, PATH, order.getIdOrder().toString());
    }

    private List<OrderItem> createOrderItemsForOrderFromCartItems(List<CartItem> cartItems, Order order) {
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

    private List<OrderItem> createOrderItemsForOrderFromSessionStorage(Map<String, Integer> cartItemsSessionStorage, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : cartItemsSessionStorage.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            Optional<Product> product = productsRepository.findProductByIdProducts(Long.valueOf(key));

            OrderItem orderItem = new OrderItem();
            orderItem.setName(product.get().getName());
            orderItem.setPrice(product.get().getPrice());
            orderItem.setQuantity(value);
            orderItem.setIdOrder(order.getIdOrder());
            orderItems.add(orderItem);
            orderItemRepository.save(orderItem);
        }

        return orderItems;
    }

    private List<InvoiceRow> generateInvoiceRows(List<OrderItem> orderItems){
        List<InvoiceRow> invoiceRows = new ArrayList<>();

        for (OrderItem item : orderItems) {
            InvoiceRow row = InvoiceRow.builder()
                    .name(item.getName())
                    .quantity(item.getQuantity())
                    .price(BigDecimal.valueOf(item.getPrice()))
                    .build();
            invoiceRows.add(row);
        }
        return invoiceRows;
    }
}