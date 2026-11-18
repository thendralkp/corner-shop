package com.example.shop.controller;

import com.example.shop.model.AppUser;
import com.example.shop.repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    // POST /login and POST /logout are handled by Spring Security itself.
    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/register")
    String registerForm(Model model) {
        model.addAttribute("form", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    String register(@Valid @ModelAttribute("form") RegisterForm form, BindingResult result) {
        if (!result.hasFieldErrors("username") && users.existsByUsername(form.getUsername())) {
            result.rejectValue("username", "taken", "That username is taken. Try another.");
        }
        if (result.hasErrors()) {
            return "register";
        }
        users.save(new AppUser(form.getUsername(), passwordEncoder.encode(form.getPassword())));
        return "redirect:/login?registered";
    }
}
