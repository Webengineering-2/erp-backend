package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

    @Mock CreationService creationService;
    @InjectMocks ProductResource resource;

    @Test
    void getProductsDelegates() {
        Product p = new Product();
        when(creationService.getMatchingProducts("q")).thenReturn(List.of(p));
        assertEquals(List.of(p), resource.getProducts("q"));
    }

    @Test
    void getProductDelegates() {
        Product p = new Product();
        when(creationService.getProductById(5L)).thenReturn(p);
        assertSame(p, resource.getProduct(5L));
    }

    @Test
    void createSavesAndReturnsOk() {
        Product p = new Product();
        Response resp = resource.create(p);
        verify(creationService).saveProduct(p);
        assertEquals(200, resp.getStatus());
        assertSame(p, resp.getEntity());
    }

    @Test
    void updateMissingProductReturnsBadRequest() {
        when(creationService.getProductById(9L)).thenReturn(null);
        Response resp = resource.update(9L, new Product());
        assertEquals(400, resp.getStatus());
    }

    @Test
    void updateCopiesFieldsResolvesCategoryAndSaves() {
        Product existing = new Product();
        existing.setId(1L);
        when(creationService.getProductById(1L)).thenReturn(existing);

        Category resolvedCategory = new Category();
        resolvedCategory.setId(7L);
        when(creationService.getCategoryById(7L)).thenReturn(resolvedCategory);

        Category inputCategory = new Category();
        inputCategory.setId(7L);
        Product input = new Product();
        input.setName("New name");
        input.setUnitPrice(new BigDecimal("9.9900"));
        input.setCategory(inputCategory);

        Response resp = resource.update(1L, input);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(creationService).saveProduct(captor.capture());
        Product saved = captor.getValue();
        assertSame(existing, saved, "must update the existing managed product, not the input");
        assertEquals("New name", saved.getName());
        assertEquals(new BigDecimal("9.9900"), saved.getUnitPrice());
        assertSame(resolvedCategory, saved.getCategory(), "category must be re-resolved from the DB");
        assertEquals(200, resp.getStatus());
        assertSame(existing, resp.getEntity());
    }

    @Test
    void deleteDelegatesAndReturnsNoContent() {
        Response resp = resource.delete(3L);
        verify(creationService).deleteProduct(3L);
        assertEquals(204, resp.getStatus());
    }
}
