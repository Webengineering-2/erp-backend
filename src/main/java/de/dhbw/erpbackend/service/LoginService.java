package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
@Transactional
public class LoginService {

    static final String GENERIC_ERROR = "Benutzername oder Passwort ist falsch.";

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordService passwordService;

    public User authenticate(String rawUsername, String password) {
        if (rawUsername == null || rawUsername.trim().isEmpty()
                || password == null || password.isEmpty()) {
            throw new UserFacingException("Bitte Benutzername und Passwort angeben.");
        }

        Optional<User> found = userRepository.findByUsername(rawUsername.trim());
        if (found.isEmpty()) {
            throw new UserFacingException(GENERIC_ERROR);
        }
        User user = found.get();
        if (!passwordService.verify(password, user.getPasswordHash())) {
            throw new UserFacingException(GENERIC_ERROR);
        }
        return user;
    }
}
