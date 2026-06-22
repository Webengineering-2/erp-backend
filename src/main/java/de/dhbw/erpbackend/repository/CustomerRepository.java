package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Customer;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Query("where name ilike concat('%', :name,'%')")
    List<Customer> findByNameLike(String name);
}
