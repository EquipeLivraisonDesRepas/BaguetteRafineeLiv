package com.fooddelivery.fooddeliveryapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.ORDINAL)
    Status status ;
    Double totalPrice ;
    LocalDateTime orderDate;
    @ManyToOne
    User user ;
    @ManyToMany
    List<Dish> dishes ;
    @ManyToOne
    private DeliveryPerson deliveryPerson;



}
