package com.fooddelivery.fooddeliveryapp.entities;

public class LoginRequest {

    private String email;  // Change from username to email
    private String password;

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
