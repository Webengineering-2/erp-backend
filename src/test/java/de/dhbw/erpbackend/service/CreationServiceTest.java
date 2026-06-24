package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.repository.CategoryRepository;
import de.dhbw.erpbackend.repository.CustomerRepository;
import de.dhbw.erpbackend.repository.LocationRepository;
import de.dhbw.erpbackend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit tests for CreationService with mocked Jakarta Data repositories.
 * Mirrors the Mockito pattern used by ItemServiceTest/OnboardingServiceTest:
 * we verify the query-branch selection, the null-id guards, save delegation,
 * and the "delete only if present" behavior. Actual query/persistence is
 * covered by RepositoryIntegrationTest.
 */
@ExtendWith(MockitoExtension.class)
class CreationServiceTest {

    @Mock ProductRepository productRepository;
    @Mock CategoryRepository categoryRepository;
    @Mock LocationRepository locationRepository;
    @Mock CustomerRepository customerRepository;

    @InjectMocks CreationService service;

    // ---------- Products ----------

    @Test
    void getMatchingProductsBlankReturnsAll() {
        Product a = new Product();
        Product b = new Product();
        when(productRepository.findAll()).thenReturn(Stream.of(a, b));

        List<Product> result = service.getMatchingProducts("   ");

        assertEquals(List.of(a, b), result);
        verify(productRepository, never()).findByNameLike(any());
    }

    @Test
    void getMatchingProductsNullReturnsAll() {
        when(productRepository.findAll()).thenReturn(Stream.empty());

        assertEquals(List.of(), service.getMatchingProducts(null));
        verify(productRepository, never()).findByNameLike(any());
    }

    @Test
    void getMatchingProductsWithQueryUsesNameLike() {
        Product p = new Product();
        when(productRepository.findByNameLike("cola")).thenReturn(List.of(p));

        List<Product> result = service.getMatchingProducts("cola");

        assertEquals(List.of(p), result);
        verify(productRepository, never()).findAll();
    }

    @Test
    void getProductByIdNullReturnsNullWithoutRepoCall() {
        assertNull(service.getProductById(null));
        verifyNoInteractions(productRepository);
    }

    @Test
    void getProductByIdReturnsRepositoryResult() {
        Product p = new Product();
        when(productRepository.findById(7L)).thenReturn(Optional.of(p));
        assertSame(p, service.getProductById(7L));
    }

    @Test
    void getProductByIdMissingReturnsNull() {
        when(productRepository.findById(7L)).thenReturn(Optional.empty());
        assertNull(service.getProductById(7L));
    }

    @Test
    void saveProductDelegates() {
        Product p = new Product();
        service.saveProduct(p);
        verify(productRepository).save(p);
    }

    @Test
    void deleteProductPresentDeletes() {
        Product p = new Product();
        when(productRepository.findById(7L)).thenReturn(Optional.of(p));
        service.deleteProduct(7L);
        verify(productRepository).delete(p);
    }

    @Test
    void deleteProductMissingDoesNothing() {
        when(productRepository.findById(7L)).thenReturn(Optional.empty());
        service.deleteProduct(7L);
        verify(productRepository, never()).delete(any());
    }

    // ---------- Categories ----------

    @Test
    void getMatchingCategoriesBlankReturnsAll() {
        Category c = new Category();
        when(categoryRepository.findAll()).thenReturn(Stream.of(c));
        assertEquals(List.of(c), service.getMatchingCategories(""));
        verify(categoryRepository, never()).findByNameLike(any());
    }

    @Test
    void getMatchingCategoriesWithQueryUsesNameLike() {
        Category c = new Category();
        when(categoryRepository.findByNameLike("drinks")).thenReturn(List.of(c));
        assertEquals(List.of(c), service.getMatchingCategories("drinks"));
    }

    @Test
    void getCategoryByIdNullReturnsNullWithoutRepoCall() {
        assertNull(service.getCategoryById(null));
        verifyNoInteractions(categoryRepository);
    }

    @Test
    void saveCategoryDelegates() {
        Category c = new Category();
        service.saveCategory(c);
        verify(categoryRepository).save(c);
    }

    @Test
    void deleteCategoryMissingDoesNothing() {
        when(categoryRepository.findById(3L)).thenReturn(Optional.empty());
        service.deleteCategory(3L);
        verify(categoryRepository, never()).delete(any());
    }

    @Test
    void deleteCategoryPresentDeletes() {
        Category c = new Category();
        when(categoryRepository.findById(3L)).thenReturn(Optional.of(c));
        service.deleteCategory(3L);
        verify(categoryRepository).delete(c);
    }

    // ---------- Locations ----------

    @Test
    void getMatchingLocationsBlankReturnsAll() {
        Location l = new Location();
        when(locationRepository.findAll()).thenReturn(Stream.of(l));
        assertEquals(List.of(l), service.getMatchingLocations(null));
        verify(locationRepository, never()).findByNameLike(any());
    }

    @Test
    void getMatchingLocationsWithQueryUsesNameLike() {
        Location l = new Location();
        when(locationRepository.findByNameLike("shelf")).thenReturn(List.of(l));
        assertEquals(List.of(l), service.getMatchingLocations("shelf"));
    }

    @Test
    void getLocationByIdNullReturnsNullWithoutRepoCall() {
        assertNull(service.getLocationById(null));
        verifyNoInteractions(locationRepository);
    }

    @Test
    void saveLocationDelegates() {
        Location l = new Location();
        service.saveLocation(l);
        verify(locationRepository).save(l);
    }

    @Test
    void deleteLocationPresentDeletes() {
        Location l = new Location();
        when(locationRepository.findById(5L)).thenReturn(Optional.of(l));
        service.deleteLocation(5L);
        verify(locationRepository).delete(l);
    }

    // ---------- Customers ----------

    @Test
    void getMatchingCustomersBlankReturnsAll() {
        Customer c = new Customer();
        when(customerRepository.findAll()).thenReturn(Stream.of(c));
        assertEquals(List.of(c), service.getMatchingCustomers("  "));
        verify(customerRepository, never()).findByNameLike(any());
    }

    @Test
    void getMatchingCustomersWithQueryUsesNameLike() {
        Customer c = new Customer();
        when(customerRepository.findByNameLike("bob")).thenReturn(List.of(c));
        assertEquals(List.of(c), service.getMatchingCustomers("bob"));
    }

    @Test
    void getCustomerByIdNullReturnsNullWithoutRepoCall() {
        assertNull(service.getCustomerById(null));
        verifyNoInteractions(customerRepository);
    }

    @Test
    void saveCustomerDelegates() {
        Customer c = new Customer();
        service.saveCustomer(c);
        verify(customerRepository).save(c);
    }

    @Test
    void deleteCustomerPresentDeletes() {
        Customer c = new Customer();
        when(customerRepository.findById(9L)).thenReturn(Optional.of(c));
        service.deleteCustomer(9L);
        verify(customerRepository).delete(c);
    }
}
