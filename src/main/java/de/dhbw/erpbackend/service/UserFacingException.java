package de.dhbw.erpbackend.service;

public class UserFacingException extends RuntimeException {

    public UserFacingException(String message) {
        super(message);
    }

    public UserFacingException(String message, Throwable cause) {
        super(message, cause);
    }
}
