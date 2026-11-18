package com.example.shop.controller;

import com.example.shop.model.CartItem;
import com.example.shop.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {

    private final CartService cart;

    public CartController(CartService cart) {
        this.cart = cart;
    }

    @GetMapping("/cart")
    String view(Principal principal, Model model) {
        List<CartItem> items = cart.getItems(principal.getName());
        model.addAttribute("items", items);
        model.addAttribute("total", cart.total(items));
        return "cart";
    }

    @PostMapping("/cart/add/{productId}")
    String add(@PathVariable Long productId, Principal principal) {
        cart.add(principal.getName(), productId);
        return "redirect:/";
    }

    @PostMapping("/cart/update/{itemId}")
    String update(@PathVariable Long itemId, @RequestParam int quantity, Principal principal) {
        cart.updateQuantity(principal.getName(), itemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{itemId}")
    String remove(@PathVariable Long itemId, Principal principal) {
        cart.remove(principal.getName(), itemId);
        return "redirect:/cart";
    }
}
