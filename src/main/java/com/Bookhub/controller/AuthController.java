package com.Bookhub.controller;

import com.Bookhub.entity.User;
import com.Bookhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Mostrar formulario de login
    @GetMapping("/login")
    public String login() {
        return "login"; // Thymeleaf template login.html
    }

    // Mostrar formulario de registro
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Thymeleaf template register.html
    }

    // Procesar registro
    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        // Validar si username o email ya existen
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("errorUsername", "El nombre de usuario ya está en uso");
            return "register";
        }
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("errorEmail", "El email ya está en uso");
            return "register";
        }

        // Registrar usuario (contraseña encriptada y rol USER)
        userService.register(user);

        // Redirigir al login después de registro
        return "redirect:/login";
    }

    // Logout se maneja automáticamente por Spring Security (/logout)
}