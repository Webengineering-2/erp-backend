package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Category;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
