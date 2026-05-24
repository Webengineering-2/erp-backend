package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.User;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Find
    Optional<User> findByUsername(String username);
}
