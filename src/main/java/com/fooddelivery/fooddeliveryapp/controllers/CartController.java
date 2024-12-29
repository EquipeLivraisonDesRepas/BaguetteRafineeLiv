package com.fooddelivery.fooddeliveryapp.controllers;

import com.fooddelivery.fooddeliveryapp.entities.Cart;
import com.fooddelivery.fooddeliveryapp.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        Cart cart = cartService.getCartForUser(userId);
        return ResponseEntity.ok(cart);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{userId}/add/{dishId}")
    public ResponseEntity<Cart> addDishToCart(@PathVariable Long userId, @PathVariable Long dishId) {
        Cart cart = cartService.addDishToCart(userId, dishId);
        return ResponseEntity.ok(cart);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{userId}/remove/{dishId}")
    public ResponseEntity<Cart> removeDishFromCart(@PathVariable Long userId, @PathVariable Long dishId) {
        Cart cart = cartService.removeDishFromCart(userId, dishId);
        return ResponseEntity.ok(cart);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{userId}/checkout")
    public ResponseEntity<String> checkoutCart(@PathVariable Long userId) {
        cartService.checkoutCart(userId);
        return ResponseEntity.ok("Cart checked out successfully!");
    }
}

