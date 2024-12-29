package com.fooddelivery.fooddeliveryapp.controllers;

import com.fooddelivery.fooddeliveryapp.dto.LoginResponse;
import com.fooddelivery.fooddeliveryapp.entities.LoginRequest;
import com.fooddelivery.fooddeliveryapp.security.JWTUtil;
import com.fooddelivery.fooddeliveryapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JWTUtil jwtUtil;


    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        logger.info("Requête de connexion reçue pour l'email: {}", loginRequest.getEmail());

        try {
            logger.debug("Tentative de connexion avec l'email: {}", loginRequest.getEmail());

            // Appeler le service d'authentification
            String token = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            logger.info("Authentification réussie pour l'email: {}", loginRequest.getEmail());

            // Retourner un objet JSON avec le token JWT
            return ResponseEntity.ok().body(new LoginResponse(token));

        } catch (RuntimeException e) {
            logger.error("Échec de l'authentification pour l'email: {}. Erreur: {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Identifiants invalides");
        } catch (Exception e) {
            logger.error("Erreur interne lors de l'authentification pour l'email: {}. Erreur: {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue.");
        }
    }
    @GetMapping("/user-id")
    public ResponseEntity<Object> getUserId(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            logger.info("Récupération de l'ID utilisateur connecté");

            // Extraire le token JWT de l'en-tête Authorization
            String token = authorizationHeader.replace("Bearer ", "");

            // Extraire l'ID utilisateur à partir du token
            String userId = jwtUtil.extractUserId(token);

            logger.info("ID utilisateur récupéré avec succès: {}", userId);

            // Retourner l'ID utilisateur
            return ResponseEntity.ok().body(userId);

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de l'ID utilisateur. Erreur: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide ou expiré");
        }
    }

}
