package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Product;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Find
    List<Product> findByCategory(Category category);
}
