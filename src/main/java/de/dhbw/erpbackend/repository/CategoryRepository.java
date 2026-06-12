package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Product;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Query("where name ilike concat('%', :name,'%')")
    List<Category> findByNameLike(String name);
}
