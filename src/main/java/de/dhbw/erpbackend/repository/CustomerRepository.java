package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.Product;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Find
    List<Customer> findByNameLike(String name);
}
