package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CreationService {
    @Inject
    private ProductRepository productRepository;
    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private LocationRepository locationRepository;
    @Inject
    private CategoryRepository categoryRepository;

    public List<Product> getMatchingProducts(String query) {
        if (query == null || query.isBlank()) {
            return productRepository.findAll().toList();
        }
        return productRepository.findByNameLike("%" + query + "%");
    }

    public List<Category> getMatchingCategories(String query) {
        if (query == null || query.isBlank()) {
            return categoryRepository.findAll().toList();
        }
        return categoryRepository.findByNameLike("%" + query + "%");
    }

    public List<Location> getMatchingLocations(String query) {
        if (query == null || query.isBlank()) {
            return locationRepository.findAll().toList();
        }
        return locationRepository.findByNameLike("%" + query + "%");
    }

    public List<Customer> getMatchingCustomers(String query) {
        if (query == null || query.isBlank()) {
            return customerRepository.findAll().toList();
        }
        return customerRepository.findByNameLike("%" + query + "%");
    }
}
