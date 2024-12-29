package com.fooddelivery.fooddeliveryapp.controllers;

import com.fooddelivery.fooddeliveryapp.entities.Dish;
import com.fooddelivery.fooddeliveryapp.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dishes")
@CrossOrigin(origins = "http://localhost:4200")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    // Ajouter un plat
    @PostMapping
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish) {
        Dish createdDish = dishService.addDish(dish);
        return new ResponseEntity<>(createdDish, HttpStatus.CREATED);
    }

    // Mettre à jour un plat
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable Long id, @RequestBody Dish dishDetails) {
        try {
            Dish updatedDish = dishService.updateDish(id, dishDetails);
            return new ResponseEntity<>(updatedDish, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer un plat
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDish(@PathVariable Long id) {
        try {
            dishService.deleteDish(id);
            return new ResponseEntity<>("Dish deleted successfully", HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Dish not found", HttpStatus.NOT_FOUND);
        }
    }

    // Récupérer tous les plats (avec filtres et tri)
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<Iterable<Dish>> getAllDishes(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String category) {
        Iterable<Dish> dishes = dishService.getAllDishes(filter, sortBy, category);
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }
}
