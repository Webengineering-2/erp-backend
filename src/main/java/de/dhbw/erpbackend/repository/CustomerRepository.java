package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Customer;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
