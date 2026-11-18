package com.example.shop.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterForm {

    @NotBlank(message = "Enter a username.")
    @Size(min = 3, max = 30, message = "Use 3 to 30 characters.")
    @Pattern(regexp = "^[A-Za-z0-9_.-]*$", message = "Use letters, numbers, dots, dashes or underscores.")
    private String username;

    @NotBlank(message = "Enter a password.")
    @Size(min = 8, max = 72, message = "Use 8 to 72 characters.")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username == null ? null : username.trim(); }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
