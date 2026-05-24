package de.dhbw.erpbackend.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordService {

    private static final int COST = 12;

    public String hash(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("password must not be empty");
        }
        return BCrypt.withDefaults().hashToString(COST, plainPassword.toCharArray());
    }

    public boolean verify(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null) return false;
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), storedHash).verified;
    }
}
