package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Customer;
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
class CustomerResourceTest {

    @Mock CreationService creationService;
    @Mock LogService logService;
    @Mock HttpServletRequest request;
    @Mock HttpSession session;
    @InjectMocks CustomerResource resource;

    private void loggedInAsAlice() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");
    }

    @Test
    void getCustomerListDelegates() {
        Customer c = new Customer();
        when(creationService.getMatchingCustomers("q")).thenReturn(List.of(c));
        assertEquals(List.of(c), resource.getCustomer("q"));
    }

    @Test
    void getCustomerByIdDelegates() {
        Customer c = new Customer();
        when(creationService.getCustomerById(5L)).thenReturn(c);
        assertSame(c, resource.getCustomer(5L));
    }

    @Test
    void createSavesLogsAndReturnsOk() {
        loggedInAsAlice();
        Customer c = new Customer();
        c.setName("Bob");
        Response resp = resource.create(c);
        verify(creationService).saveCustomer(c);
        verify(logService).log(eq("alice"), eq(LogType.CUSTOMER_CREATED), anyString());
        assertEquals(200, resp.getStatus());
        assertSame(c, resp.getEntity());
    }

    @Test
    void updateSetsIdFromPathLogsAndSaves() {
        loggedInAsAlice();
        Customer c = new Customer();
        Response resp = resource.update(8L, c);
        assertEquals(8L, c.getId(), "path id must be applied to the entity before saving");
        verify(creationService).saveCustomer(c);
        verify(logService).log(eq("alice"), eq(LogType.CUSTOMER_UPDATED), anyString());
        assertEquals(200, resp.getStatus());
    }

    @Test
    void deleteDelegatesLogsAndReturnsNoContent() {
        loggedInAsAlice();
        Response resp = resource.delete(3L);
        verify(creationService).deleteCustomer(3L);
        verify(logService).log(eq("alice"), eq(LogType.CUSTOMER_DELETED), anyString());
        assertEquals(204, resp.getStatus());
    }
}
