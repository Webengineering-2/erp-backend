package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Objects;

@ApplicationScoped
public class OnboardingService {

    static final int USERNAME_MIN = 3;
    static final int USERNAME_MAX = 64;

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordService passwordService;

    @Transactional
    public User register(String rawUsername, String password, String passwordRepeat) {
        String username = validateUsername(rawUsername);
        validatePasswords(password, passwordRepeat);

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserFacingException("Dieser Benutzername ist bereits vergeben.");
        }

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordService.hash(password));
        return userRepository.insert(user);
    }

    private String validateUsername(String raw) {
        if (raw == null) throw new UserFacingException("Bitte einen Benutzernamen angeben.");
        String trimmed = raw.trim();
        if (trimmed.length() < USERNAME_MIN || trimmed.length() > USERNAME_MAX) {
            throw new UserFacingException(
                    "Benutzername muss zwischen " + USERNAME_MIN + " und " + USERNAME_MAX + " Zeichen lang sein.");
        }
        return trimmed;
    }

    private void validatePasswords(String password, String passwordRepeat) {
        if (password == null || password.isEmpty()) {
            throw new UserFacingException("Bitte ein Passwort angeben.");
        }
        if (!Objects.equals(password, passwordRepeat)) {
            throw new UserFacingException("Die Passwörter stimmen nicht überein.");
        }
    }
}
