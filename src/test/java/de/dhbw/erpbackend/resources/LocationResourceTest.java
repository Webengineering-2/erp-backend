package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Location;
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
class LocationResourceTest {

    @Mock CreationService creationService;
    @InjectMocks LocationRessource resource;

    @Test
    void getLocationListDelegates() {
        Location l = new Location();
        when(creationService.getMatchingLocations("q")).thenReturn(List.of(l));
        assertEquals(List.of(l), resource.getLocation("q"));
    }

    @Test
    void getLocationByIdDelegates() {
        Location l = new Location();
        when(creationService.getLocationById(5L)).thenReturn(l);
        assertSame(l, resource.getLocation(5L));
    }

    @Test
    void createSavesAndReturnsOk() {
        Location l = new Location();
        Response resp = resource.create(l);
        verify(creationService).saveLocation(l);
        assertEquals(200, resp.getStatus());
        assertSame(l, resp.getEntity());
    }

    @Test
    void updateSetsIdFromPathThenSaves() {
        Location l = new Location();
        Response resp = resource.update(8L, l);
        assertEquals(8L, l.getId(), "path id must be applied to the entity before saving");
        verify(creationService).saveLocation(l);
        assertEquals(200, resp.getStatus());
    }

    @Test
    void deleteDelegatesAndReturnsNoContent() {
        Response resp = resource.delete(3L);
        verify(creationService).deleteLocation(3L);
        assertEquals(204, resp.getStatus());
    }
}
