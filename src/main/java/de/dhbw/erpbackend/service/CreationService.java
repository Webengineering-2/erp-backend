package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
@Transactional
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
        return productRepository.findAll().filter(p -> p.getName() != null &&
                        p.getName().toLowerCase().contains(query.toLowerCase())).toList();
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
        return categoryRepository.findAll().filter(p -> p.getName() != null &&
                p.getName().toLowerCase().contains(query.toLowerCase())).toList();
    }

    public Category getCategoriesById(Long id){
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
        return locationRepository.findAll().filter(p -> p.getName() != null &&
                p.getName().toLowerCase().contains(query.toLowerCase())).toList();
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
        return customerRepository.findAll().filter(p -> p.getName() != null &&
                p.getName().toLowerCase().contains(query.toLowerCase())).toList();
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
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product != null) {
            productRepository.delete(product);
        }

    }
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category != null) {
            categoryRepository.delete(category);
        }
    }
    @Transactional
    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id).orElse(null);
        if (location != null) {
            locationRepository.delete(location);
        }
    }
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customerRepository.delete(customer);
        }
    }
}
