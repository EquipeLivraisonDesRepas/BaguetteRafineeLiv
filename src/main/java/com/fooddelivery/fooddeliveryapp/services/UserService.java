package com.fooddelivery.fooddeliveryapp.services;

import com.fooddelivery.fooddeliveryapp.entities.User;

public interface UserService {
    User registerUser(User user);
    
    User updateUser(Long userId, User updatedUser);
    User getUserProfile(Long userId);
}
