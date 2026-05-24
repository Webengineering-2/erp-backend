package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager em;

    public boolean anyUserExists() {
        Long count = em.createQuery("select count(u) from User u", Long.class).getSingleResult();
        return count != null && count > 0;
    }

    public Optional<User> findByUsername(String username) {
        List<User> results = em.createQuery(
                        "select u from User u where u.username = :name", User.class)
                .setParameter("name", username)
                .setMaxResults(1)
                .getResultList();
        return results.stream().findFirst();
    }
}
