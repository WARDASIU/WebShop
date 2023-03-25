package com.wardasiu.project.wardasiu.controllers;

import com.wardasiu.project.wardasiu.entities.*;
import com.wardasiu.project.wardasiu.repositories.ProductsRepository;
import com.wardasiu.project.wardasiu.security.UserService;
import com.wardasiu.project.wardasiu.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RestController
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    public ProductsRepository productsRepository;

    @Autowired
    private EmailService emailService;

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
















//    @PostMapping("/checkout")
//    public String prepareOrder(@AuthenticationPrincipal UserDetails userDetails,
//                               HttpSession session) {
//        String username = userDetails.getUsername();
//        User user = userService.findUserByUsername(username);
//
//        List<Product> products = new ArrayList<>(Collections.emptyList());
//
//        for (int i = 0; i < productIds.size(); i++) {
//            products.add(i, productsRepository.findProductByIdProducts(productIds.get(i)).get());
//        }
//
//        List<OrderItem> items = new ArrayList<>();
//        for (int i = 0; i < products.size(); i++) {
//            items.add(new OrderItem(products.get(i), quantities.get(i)));
//        }
//        Order order = new Order(items);
//        session.setAttribute("order", order);
//
//        return "redirect:https://payu.com/pay";
//    }


//    @PostMapping("/createOrder")
//    public ResponseEntity<String> createOrder(@RequestBody OrderCreateRequest request) throws Exception {
//        String url = "https://secure.snd.payu.com/api/v2_1/orders";
//        String authorization = "Bearer d9a4536e-62ba-4f60-8017-6053211d3f47";
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(authorization);
//
//        HttpEntity<OrderCreateRequest> entity = new HttpEntity<OrderCreateRequest>(request, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//        return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
//    }
}