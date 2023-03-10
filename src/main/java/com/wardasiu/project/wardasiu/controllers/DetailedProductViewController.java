package com.wardasiu.project.wardasiu.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
class DetailedProductViewController {
    @GetMapping("/detailed-view/product/{Id}")
    public ModelAndView returnDetailedProductViewById(@PathVariable(value = "Id") long id) {
        ModelAndView modelAndView = new ModelAndView("index");

        return modelAndView;
    }

    @GetMapping("/detailed-view")
    public ModelAndView returnDetailedProductView() {
        return new ModelAndView("detailed-product-view");
    }
}