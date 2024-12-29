package com.fooddelivery.fooddeliveryapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    User user;

    @ManyToMany
    @JoinTable(
            name = "cart_dishes",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    List<Dish> dishes = new ArrayList<>();  // Initialisation de la liste

    @Enumerated(EnumType.ORDINAL)
    Status status;

    Double totalPrice;

    // Constructeur par défaut pour éviter toute tentative d'accès à une liste null
    public Cart() {
        this.dishes = new ArrayList<>();
    }

}
