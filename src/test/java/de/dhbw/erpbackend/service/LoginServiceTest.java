package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    UserRepository userRepository;

    LoginService service;
    PasswordService passwordService;

    @BeforeEach
    void setUp() {
        passwordService = new PasswordService();
        service = new LoginService();
        service.userRepository = userRepository;
        service.passwordService = passwordService;
    }

    @Test
    void authenticatesValidCredentials() {
        User user = new User();
        user.setUsername("alice");
        user.setPasswordHash(passwordService.hash("hunter2"));
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));

        User result = service.authenticate("alice", "hunter2");

        assertSame(user, result);
    }

    @Test
    void trimsUsername() {
        User user = new User();
        user.setUsername("alice");
        user.setPasswordHash(passwordService.hash("hunter2"));
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));

        User result = service.authenticate("  alice  ", "hunter2");

        assertSame(user, result);
    }

    @Test
    void unknownUserThrowsGenericError() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        UserFacingException ex = assertThrows(UserFacingException.class,
                () -> service.authenticate("ghost", "whatever"));
        assertEquals(LoginService.GENERIC_ERROR, ex.getMessage());
    }

    @Test
    void wrongPasswordThrowsSameGenericError() {
        User user = new User();
        user.setUsername("alice");
        user.setPasswordHash(passwordService.hash("hunter2"));
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));

        UserFacingException ex = assertThrows(UserFacingException.class,
                () -> service.authenticate("alice", "nope"));
        assertEquals(LoginService.GENERIC_ERROR, ex.getMessage());
    }

    @Test
    void blankUsernameRejected() {
        assertThrows(UserFacingException.class,
                () -> service.authenticate("   ", "secret"));
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    void blankPasswordRejected() {
        assertThrows(UserFacingException.class,
                () -> service.authenticate("alice", ""));
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    void nullInputsRejected() {
        assertThrows(UserFacingException.class,
                () -> service.authenticate(null, "secret"));
        assertThrows(UserFacingException.class,
                () -> service.authenticate("alice", null));
    }
}
