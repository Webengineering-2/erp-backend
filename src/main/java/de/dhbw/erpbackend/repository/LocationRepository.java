package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Location;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
    @Query("where name ilike concat('%', :name,'%')")
    List<Location> findByNameLike(String name);
}
