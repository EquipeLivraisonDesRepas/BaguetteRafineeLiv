package com.fooddelivery.fooddeliveryapp.services;

import com.fooddelivery.fooddeliveryapp.entities.Cart;
import com.fooddelivery.fooddeliveryapp.entities.Dish;
import com.fooddelivery.fooddeliveryapp.entities.Status;
import com.fooddelivery.fooddeliveryapp.repositories.CartRepository;
import com.fooddelivery.fooddeliveryapp.repositories.DishRepository;
import com.fooddelivery.fooddeliveryapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;  // Déclaration du UserRepository

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, DishRepository dishRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;  // Injection du UserRepository
    }

    @Override
    public Cart getCartForUser(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart addDishToCart(Long userId, Long dishId) {
        Optional<Dish> dish = dishRepository.findById(dishId);
        if (dish.isPresent()) {
            Cart cart = getCartForUser(userId);
            if (cart == null) {
                cart = new Cart();
                cart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
                cart.setTotalPrice(0.0);
                cart.setStatus(Status.LIVRE); // Status par défaut
            }
            cart.getDishes().add(dish.get()); // L'ajout se fait maintenant sans problème de null
            cart.setTotalPrice(cart.getTotalPrice() + dish.get().getPrice());
            return cartRepository.save(cart);
        }
        return null;
    }

    @Override
    public Cart removeDishFromCart(Long userId, Long dishId) {
        Cart cart = getCartForUser(userId);
        Optional<Dish> dish = dishRepository.findById(dishId);
        if (dish.isPresent()) {
            cart.getDishes().remove(dish.get());  // Suppression du plat du panier
            cart.setTotalPrice(cart.getTotalPrice() - dish.get().getPrice());  // Mise à jour du prix total
            return cartRepository.save(cart);  // Sauvegarde du panier
        }
        return null;
    }

    @Override
    public Cart updateCart(Long userId, Cart cart) {
        return cartRepository.save(cart);  // Sauvegarde de la mise à jour du panier
    }

    @Override
    public void checkoutCart(Long userId) {
        Cart cart = getCartForUser(userId);
        cart.setStatus(Status.LIVRE);  // Mise à jour du statut à "LIVRE"
        cartRepository.save(cart);  // Sauvegarde du panier après checkout
    }
}
