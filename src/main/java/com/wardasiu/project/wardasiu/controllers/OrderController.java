package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.entities.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/prepareOrder")
public class OrderController {
    @GetMapping
    public ModelAndView returnPrepareOrderPage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("prepareOrder");
        boolean isLoggedIn = authentication != null;
        modelAndView.addObject("isLoggedIn", isLoggedIn);

        if (isLoggedIn) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
            modelAndView.addObject("isAdmin", isAdmin);
        }

        return modelAndView;
    }

//    @PostMapping("/checkout")
//    public ResponseEntity<String> checkout(@RequestBody Cart cart) {
//        Order order = orderService.createOrder(cart);
//        BigDecimal amount = order.getTotalAmount();
//        PaymentRequest paymentRequest = paymentService.preparePayment(amount, order.getId());
//        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
//                .setAmount(amount.longValue())
//                .setCurrency("usd")
//                .setDescription("Zamówienie nr " + order.getId())
//                .setStatementDescriptor("Zamówienie nr " + order.getId())
//                .addPaymentMethodType("card")
//                .setReceiptEmail(order.getUser().getEmail())
//                .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
//                .setReturnUrl("http://localhost:8080/payment/success")
//                .setCancelUrl("http://localhost:8080/payment/cancel")
//                .putMetadata("orderId", order.getId().toString())
//                .build();
//        PaymentIntent paymentIntent = PaymentIntent.create(params);
//        // implementacja logiki obsługi płatności
//    }
}