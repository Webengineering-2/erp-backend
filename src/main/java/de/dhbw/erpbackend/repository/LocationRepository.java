package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Location;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
}
