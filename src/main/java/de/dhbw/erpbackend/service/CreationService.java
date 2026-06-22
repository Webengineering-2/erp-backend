package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.util.List;

@Named
@ApplicationScoped
@Transactional
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
        return productRepository.findByNameLike(query);
    }

    public Product getProductById(Long id){
        if (id == null) {
            return null;
        }
        return productRepository.findById(id).orElse(null);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public List<Category> getMatchingCategories(String query) {
        if (query == null || query.isBlank()) {
            return categoryRepository.findAll().toList();
        }
        return categoryRepository.findByNameLike(query);
    }

    public Category getCategoryById(Long id){
        if (id == null) {
            return null;
        }
        return categoryRepository.findById(id).orElse(null);
    }

    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

    public List<Location> getMatchingLocations(String query) {
        if (query == null || query.isBlank()) {
            return locationRepository.findAll().toList();
        }
        return locationRepository.findByNameLike(query);
    }

    public Location getLocationById(Long id){
        if (id == null) {
            return null;
        }
        return locationRepository.findById(id).orElse(null);
    }

    public void saveLocation(Location location){
        locationRepository.save(location);
    }

    public List<Customer> getMatchingCustomers(String query) {
        if (query == null || query.isBlank()) {
            return customerRepository.findAll().toList();
        }
        return customerRepository.findByNameLike(query );
    }

    public Customer getCustomerById(Long id){
        if (id == null) {
            return null;
        }
        return customerRepository.findById(id).orElse(null);
    }

    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public void deleteProduct(Long id) {
        productRepository
                .findById(id)
                .ifPresent(product -> productRepository.delete(product));
    }

    public void deleteCategory(Long id) {
        categoryRepository
                .findById(id)
                .ifPresent(category -> categoryRepository.delete(category));
    }

    public void deleteLocation(Long id) {
        locationRepository
                .findById(id)
                .ifPresent(location -> locationRepository.delete(location));
    }

    public void deleteCustomer(Long id) {
        customerRepository
                .findById(id)
                .ifPresent(customer -> customerRepository.delete(customer));
    }
}
