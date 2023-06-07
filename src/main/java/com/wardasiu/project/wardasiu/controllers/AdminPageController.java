package com.wardasiu.project.wardasiu.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminPageController {
    @GetMapping
    public ModelAndView returnAdminPage() {
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("isLoggedIn", true);
        modelAndView.addObject("isAdmin", true);

        return modelAndView;
    }

    @GetMapping("/adminNewsletter")
    public ModelAndView returnNewsletterPage() {
        ModelAndView modelAndView = new ModelAndView("adminNewsletter");
        modelAndView.addObject("isLoggedIn", true);
        modelAndView.addObject("isAdmin", true);


        return modelAndView;
    }

    @GetMapping("/adminOrders")
    public ModelAndView returnOrderPage() {
        ModelAndView modelAndView = new ModelAndView("adminOrders");
        modelAndView.addObject("isLoggedIn", true);
        modelAndView.addObject("isAdmin", true);

        return modelAndView;
    }

    @GetMapping("/adminAddProduct")
    public ModelAndView returnAdminAddProduct() {
        ModelAndView modelAndView = new ModelAndView("adminAddProduct");
        modelAndView.addObject("isLoggedIn", true);
        modelAndView.addObject("isAdmin", true);

        return modelAndView;
    }

    @GetMapping("/downloadInvoice/{orderId}")
    public ResponseEntity<Resource> downloadInvoice(@PathVariable Long orderId) throws IOException {
        String filename = "Faktura_nr" + orderId + ".pdf";
        String folderPath = "D:\\PracaInz\\test\\project\\static\\invoices\\"; // Replace with the actual folder path

        File file = new File(folderPath + filename);
        Resource resource = new FileSystemResource(file);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }


}