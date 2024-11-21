package com.fooddelivery.fooddeliveryapp.controllers;

import com.fooddelivery.fooddeliveryapp.entities.User;
import com.fooddelivery.fooddeliveryapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Inscription de l'utilisateur
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // Connexion de l'utilisateur
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        return userService.loginUser(user.getEmail(), user.getPassword());
    }

    // Récupérer le profil de l'utilisateur
    @GetMapping("/profile")
    public User getUserProfile(@RequestParam Long userId) {
        return userService.getUserProfile(userId);
    }
}
