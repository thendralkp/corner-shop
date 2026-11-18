package com.example.shop.controller;

import com.example.shop.service.CartService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

/** Adds the signed-in username and cart size to every page so the header can show them. */
@ControllerAdvice
public class GlobalModelAttributes {

    private final CartService cart;

    public GlobalModelAttributes(CartService cart) {
        this.cart = cart;
    }

    @ModelAttribute("username")
    String username(Principal principal) {
        return principal == null ? null : principal.getName();
    }

    @ModelAttribute("cartCount")
    int cartCount(Principal principal) {
        return principal == null ? 0 : cart.count(principal.getName());
    }
}
