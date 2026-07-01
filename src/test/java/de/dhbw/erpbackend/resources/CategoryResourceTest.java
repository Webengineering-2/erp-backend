package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.service.CreationService;
import de.dhbw.erpbackend.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryResourceTest {

    @Mock CreationService creationService;
    @Mock LogService logService;
    @Mock HttpServletRequest request;
    @Mock HttpSession session;
    @InjectMocks CategoryResource resource;

    private void loggedInAsAlice() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");
    }

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
    void createSavesLogsAndReturnsOk() {
        loggedInAsAlice();
        Category c = new Category();
        c.setName("Drinks");
        Response resp = resource.create(c);
        verify(creationService).saveCategory(c);
        verify(logService).log(eq("alice"), eq(LogType.CATEGORY_CREATED), anyString());
        assertEquals(200, resp.getStatus());
        assertSame(c, resp.getEntity());
    }

    @Test
    void updateSetsIdFromPathLogsAndSaves() {
        loggedInAsAlice();
        Category c = new Category();
        Response resp = resource.update(8L, c);
        assertEquals(8L, c.getId(), "path id must be applied to the entity before saving");
        verify(creationService).saveCategory(c);
        verify(logService).log(eq("alice"), eq(LogType.CATEGORY_UPDATED), anyString());
        assertEquals(200, resp.getStatus());
    }

    @Test
    void deleteDelegatesLogsAndReturnsNoContent() {
        loggedInAsAlice();
        Response resp = resource.delete(3L);
        verify(creationService).deleteCategory(3L);
        verify(logService).log(eq("alice"), eq(LogType.CATEGORY_DELETED), anyString());
        assertEquals(204, resp.getStatus());
    }
}
