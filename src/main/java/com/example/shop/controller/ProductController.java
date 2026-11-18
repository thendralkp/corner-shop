package com.example.shop.controller;

import com.example.shop.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    private final ProductRepository products;

    public ProductController(ProductRepository products) {
        this.products = products;
    }

    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("products", products.findAll());
        return "products";
    }
}
