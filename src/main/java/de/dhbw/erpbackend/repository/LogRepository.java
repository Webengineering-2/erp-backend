package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Log;
import de.dhbw.erpbackend.domain.User;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log, Long> {

    @Find
    List<Log> findByUser(User user);
}
