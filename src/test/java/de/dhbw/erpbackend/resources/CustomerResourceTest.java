package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Customer;
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
class CustomerResourceTest {

    @Mock CreationService creationService;
    @InjectMocks CustomerResource resource;

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
    void createSavesAndReturnsOk() {
        Customer c = new Customer();
        Response resp = resource.create(c);
        verify(creationService).saveCustomer(c);
        assertEquals(200, resp.getStatus());
        assertSame(c, resp.getEntity());
    }

    @Test
    void updateSetsIdFromPathThenSaves() {
        Customer c = new Customer();
        Response resp = resource.update(8L, c);
        assertEquals(8L, c.getId(), "path id must be applied to the entity before saving");
        verify(creationService).saveCustomer(c);
        assertEquals(200, resp.getStatus());
    }

    @Test
    void deleteDelegatesAndReturnsNoContent() {
        Response resp = resource.delete(3L);
        verify(creationService).deleteCustomer(3L);
        assertEquals(204, resp.getStatus());
    }
}
