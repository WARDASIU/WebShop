package com.wardasiu.project.wardasiu.controllers;

import com.google.common.io.Resources;
import com.wardasiu.project.wardasiu.entities.*;
import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import com.wardasiu.project.wardasiu.security.UserService;
import com.wardasiu.project.wardasiu.service.CartService;
import com.wardasiu.project.wardasiu.service.EmailService;
import com.wardasiu.project.wardasiu.service.InvoiceGenerator;
import com.wardasiu.project.wardasiu.service.OrderService;
import com.wardasiu.project.wardasiu.service.invoice.InvoiceReceiver;
import com.wardasiu.project.wardasiu.service.invoice.InvoiceRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nullable;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;


    @GetMapping
    @RequestMapping("/prepareOrder")
    public ModelAndView returnPrepareOrderPage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("prepareOrder");
        boolean isLoggedIn = authentication != null;
        modelAndView.addObject("isLoggedIn", isLoggedIn);



        if (isLoggedIn) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
            modelAndView.addObject("isAdmin", isAdmin);
            User user = userService.findUserByUsername(authentication.getName());
            modelAndView.addObject("user", user);
        }

        return modelAndView;
    }

    @PostMapping
    @RequestMapping("/serviceOrder")
    public ResponseEntity<?> serviceOrder(Authentication authentication,
                                          @RequestParam Map<String, String> values) {
        String receiver = values.get("email");
        if (authentication != null) {
            receiver = userService.findUserByUsername(authentication.getName()).getEmail();
        }
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<h1>" + "Zamówienie usługi od Firmy EasyStep" + "</h1>");
        emailBody.append("<div style='margin: 2px 0; font-size: 1.3em; font-family: sans-serif;'");
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value != null && !value.equals("") && !Objects.equals(key, "email")) {
                emailBody.append("<p>").append(key.replace("_", " ").toUpperCase()).append(": ").append("<i>").append(value.toUpperCase()).append("\n").append("</i>").append("</p>");
            }
        }
        emailBody.append("</div>");
        emailBody.append("<h4>W przeciągu dwóch dni roboczych nasi specjaliści skontaktują się z Państwem!</h4>");

        emailService.sendHTMLEmail(receiver, "Zamówienie usługi od Firmy EasyStep", String.valueOf(emailBody));

        return ResponseEntity.ok().build();
    }

    @PostMapping
    @RequestMapping("/personalizeOrder")
    public ResponseEntity<?> personalizeOrder(Authentication authentication,
                                              @RequestParam Map<String, String> values) {
        log.info(values.toString());
        String receiver = values.get("email");
        if (authentication != null) {
            receiver = userService.findUserByUsername(authentication.getName()).getEmail();
        }
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<h1>" + "Zamówienie spersonalizowanego stołu od firmy EasyStep" + "</h1>");
        emailBody.append("<div style='margin: 2px 0; font-size: 1.3em; font-family: sans-serif;'");
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value != null && !value.equals("") && !Objects.equals(key, "email")) {
                emailBody.append("<p>").append(key.replace("_", " ").toUpperCase()).append(": ").append("<i>").append(value.toUpperCase()).append("\n").append("</i>").append("</p>");
            }
        }
        emailBody.append("</div>");
        emailBody.append("<h4>W przeciągu dwóch dni roboczych nasi specjaliści skontaktują się z Państwem!</h4>");

        emailService.sendHTMLEmail(receiver, "Zamówienie stołu od Firmy EasyStep", String.valueOf(emailBody));

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/prepareOrder/checkout")
    public ResponseEntity<?> checkout(Authentication authentication,
                                      @RequestParam @Nullable Map<String, String> values) {
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            User user = userService.findUserByUsername(authentication.getName());
            List<CartItem> cartItems = cartService.findCartItemsByUserCart(user);

            if (cartItems.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Order order = orderService.createOrder(new Order(
                    user.getIdUser(),
                    user.getAddress(),
                    user.getName(),
                    user.getSurname(),
                    user.getPostCode()
            ));

            File invoice = orderService.generateInvoiceForOrder(user, cartItems, order);
            emailService.sendHTMLEmailWithAttachments(
                    user.getEmail(), "Zamowienie nr " + order.getIdOrder() + " z firmy EasyStep", "Dane faktury:", invoice);

            cartService.deleteCartItems(cartItems);

            return ResponseEntity.ok().build();
        }
    }
}