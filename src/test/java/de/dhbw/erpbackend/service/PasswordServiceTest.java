package de.dhbw.erpbackend.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordServiceTest {

    private final PasswordService service = new PasswordService();

    @Test
    void hashIsNotPlaintextAndVerifies() {
        String hash = service.hash("hunter2");
        assertNotEquals("hunter2", hash);
        assertTrue(hash.startsWith("$2"));
        assertTrue(service.verify("hunter2", hash));
    }

    @Test
    void verifyRejectsWrongPassword() {
        String hash = service.hash("correct horse battery staple");
        assertFalse(service.verify("wrong", hash));
    }

    @Test
    void verifyHandlesNulls() {
        assertFalse(service.verify(null, "x"));
        assertFalse(service.verify("x", null));
    }

    @Test
    void hashRejectsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> service.hash(""));
        assertThrows(IllegalArgumentException.class, () -> service.hash(null));
    }
}
