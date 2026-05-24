package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Item;
import de.dhbw.erpbackend.domain.ItemStatus;
import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.domain.Product;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    @Find
    List<Item> findByProduct(Product product);

    @Find
    List<Item> findByLocation(Location location);

    @Find
    List<Item> findByStatus(ItemStatus status);
}
