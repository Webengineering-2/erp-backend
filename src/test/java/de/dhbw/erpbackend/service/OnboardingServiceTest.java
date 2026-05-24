package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OnboardingServiceTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private OnboardingService service;

    @BeforeAll
    void setupFactory() {
        Map<String, String> overrides = new HashMap<>();
        overrides.put("jakarta.persistence.jdbc.url", "jdbc:h2:mem:erp-onboarding;DB_CLOSE_DELAY=-1");
        overrides.put("hibernate.hbm2ddl.auto", "create-drop");
        emf = Persistence.createEntityManagerFactory("default", overrides);
    }

    @AfterAll
    void closeFactory() {
        if (emf != null) emf.close();
    }

    @BeforeEach
    void freshEm() {
        if (em != null && em.isOpen()) em.close();
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("delete from User").executeUpdate();
        em.getTransaction().commit();

        service = new OnboardingService();
        service.em = em;
        service.passwordService = new PasswordService();
        service.userService = new UserService();
        service.userService.em = em;
    }

    @Test
    void registersUserHashesPasswordAndPersists() {
        User u = service.register("alice", "hunter2", "hunter2");
        assertNotNull(u.getId());
        assertEquals("alice", u.getUsername());
        assertTrue(u.getPasswordHash().startsWith("$2"));

        User loaded = em.find(User.class, u.getId());
        assertEquals("alice", loaded.getUsername());
    }

    @Test
    void trimsUsername() {
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
        service.register("carol", "secret", "secret");
        assertThrows(UserFacingException.class,
                () -> service.register("carol", "secret", "secret"));
    }
}
