package com.fooddelivery.fooddeliveryapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Plats")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    String name;
    String description;
    Double price;
    String category ;
    String rating;
    @Column(columnDefinition = "LONGTEXT")  
    String image;


}
