package com.fooddelivery.fooddeliveryapp.services;

import com.fooddelivery.fooddeliveryapp.entities.User;
import com.fooddelivery.fooddeliveryapp.repositories.UserRepository;
import com.fooddelivery.fooddeliveryapp.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public String authenticate(String email, String password) {
        logger.info("Tentative de connexion avec l'email: {}", email);

        // Rechercher l'utilisateur par son email
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            logger.info("Utilisateur trouvé pour l'email: {}", email);
            logger.debug("Mot de passe dans la base de données: {}", user.get().getPassword());

            // Comparer les mots de passe
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                logger.info("Mot de passe valide pour l'email: {}", email);
                String token = jwtUtil.generateToken(user.get().getEmail());
                logger.debug("Jeton généré avec succès pour l'email: {}", email);
                return token;
            } else {
                // En cas de mot de passe invalide
                logger.warn("Mot de passe invalide pour l'email: {}", email);
                throw new RuntimeException("Mot de passe invalide");
            }
        } else {
            // Si l'utilisateur n'est pas trouvé
            logger.warn("Aucun utilisateur trouvé pour l'email: {}", email);
            throw new RuntimeException("Identifiants invalides");
        }
    }
}
