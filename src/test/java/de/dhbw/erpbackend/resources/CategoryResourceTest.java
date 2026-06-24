package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.service.CreationService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryResourceTest {

    @Mock CreationService creationService;
    @InjectMocks CategoryResource resource;

    @Test
    void getCategoryListDelegates() {
        Category c = new Category();
        when(creationService.getMatchingCategories("q")).thenReturn(List.of(c));
        assertEquals(List.of(c), resource.getCategory("q"));
    }

    @Test
    void getCategoryByIdDelegates() {
        Category c = new Category();
        when(creationService.getCategoryById(5L)).thenReturn(c);
        assertSame(c, resource.getCategory(5L));
    }

    @Test
    void createSavesAndReturnsOk() {
        Category c = new Category();
        Response resp = resource.create(c);
        verify(creationService).saveCategory(c);
        assertEquals(200, resp.getStatus());
        assertSame(c, resp.getEntity());
    }

    @Test
    void updateSetsIdFromPathThenSaves() {
        Category c = new Category();
        Response resp = resource.update(8L, c);
        assertEquals(8L, c.getId(), "path id must be applied to the entity before saving");
        verify(creationService).saveCategory(c);
        assertEquals(200, resp.getStatus());
    }

    @Test
    void deleteDelegatesAndReturnsNoContent() {
        Response resp = resource.delete(3L);
        verify(creationService).deleteCategory(3L);
        assertEquals(204, resp.getStatus());
    }
}
