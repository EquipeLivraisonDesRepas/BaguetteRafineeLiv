package com.fooddelivery.fooddeliveryapp.services;

import com.fooddelivery.fooddeliveryapp.entities.User;
import com.fooddelivery.fooddeliveryapp.repositories.UserRepository;
import com.fooddelivery.fooddeliveryapp.security.JWTUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserServiceException("The provided email is already in use.");
        }

        // Attribuer un rôle par défaut si aucun n'est défini
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        // Utiliser la méthode saveUser pour enregistrer l'utilisateur avec le mot de passe haché
        saveUser(user);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        // Récupérer l'utilisateur existant
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException("User not found."));

        // Mettre à jour les champs pertinents
        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            if (!existingUser.getEmail().equals(updatedUser.getEmail()) &&
                    userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new UserServiceException("The provided email is already in use.");
            }
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(updatedUser.getPassword());
            saveUser(existingUser); // Utilise la méthode saveUser pour le hachage
        }
        if (updatedUser.getRole() != null && !updatedUser.getRole().isEmpty()) {
            existingUser.setRole(updatedUser.getRole());
        }

        // Sauvegarder les modifications
        return userRepository.save(existingUser);
    }

    @Override
    public User getUserProfile(Long userId) {
        // Récupérer et retourner le profil utilisateur
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException("User not found."));
    }

    public void saveUser(User user) {
        // Hachage du mot de passe avant de l'enregistrer
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
