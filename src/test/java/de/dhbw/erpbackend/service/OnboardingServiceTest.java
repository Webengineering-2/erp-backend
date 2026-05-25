package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit test for OnboardingService with a mocked repository. JTA + the real
 * Jakarta Data repo implementation are exercised at runtime in the deployed
 * app; here we only verify validation, hashing, and the registration flow.
 */
@ExtendWith(MockitoExtension.class)
class OnboardingServiceTest {

    @Mock
    UserRepository userRepository;

    OnboardingService service;

    @BeforeEach
    void setUp() {
        service = new OnboardingService();
        service.userRepository = userRepository;
        service.passwordService = new PasswordService();
    }

    private void stubInsertAssignsId() {
        when(userRepository.insert(any(User.class))).thenAnswer((InvocationOnMock inv) -> {
            User u = inv.getArgument(0);
            u.setId(1L);
            return u;
        });
    }

    @Test
    void registersUserHashesPasswordAndPersists() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        stubInsertAssignsId();

        User u = service.register("alice", "hunter2", "hunter2");

        assertNotNull(u.getId());
        assertEquals("alice", u.getUsername());
        assertTrue(u.getPasswordHash().startsWith("$2"));
    }

    @Test
    void trimsUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        stubInsertAssignsId();

        User u = service.register("  bob  ", "p4ssword", "p4ssword");
        assertEquals("bob", u.getUsername());
    }

    @Test
    void rejectsMismatchedPasswords() {
        UserFacingException ex = assertThrows(UserFacingException.class,
                () -> service.register("alice", "a", "b"));
        assertTrue(ex.getMessage().toLowerCase().contains("passw"));
    }

    @Test
    void rejectsEmptyPassword() {
        assertThrows(UserFacingException.class,
                () -> service.register("alice", "", ""));
    }

    @Test
    void rejectsShortUsername() {
        assertThrows(UserFacingException.class,
                () -> service.register("ab", "secret", "secret"));
    }

    @Test
    void rejectsLongUsername() {
        String tooLong = "x".repeat(OnboardingService.USERNAME_MAX + 1);
        assertThrows(UserFacingException.class,
                () -> service.register(tooLong, "secret", "secret"));
    }

    @Test
    void rejectsDuplicateUsername() {
        User existing = new User();
        existing.setUsername("carol");
        when(userRepository.findByUsername("carol")).thenReturn(Optional.of(existing));

        UserFacingException ex = assertThrows(UserFacingException.class,
                () -> service.register("carol", "secret", "secret"));
        assertTrue(ex.getMessage().toLowerCase().contains("vergeben"));
    }
}
