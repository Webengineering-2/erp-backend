package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Location;
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
class LocationResourceTest {

    @Mock CreationService creationService;
    @Mock LogService logService;
    @Mock HttpServletRequest request;
    @Mock HttpSession session;
    @InjectMocks LocationRessource resource;

    private void loggedInAsAlice() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");
    }

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
    void createSavesLogsAndReturnsOk() {
        loggedInAsAlice();
        Location l = new Location();
        l.setName("Regal A");
        Response resp = resource.create(l);
        verify(creationService).saveLocation(l);
        verify(logService).log(eq("alice"), eq(LogType.LOCATION_CREATED), anyString());
        assertEquals(200, resp.getStatus());
        assertSame(l, resp.getEntity());
    }

    @Test
    void updateSetsIdFromPathLogsAndSaves() {
        loggedInAsAlice();
        Location l = new Location();
        Response resp = resource.update(8L, l);
        assertEquals(8L, l.getId(), "path id must be applied to the entity before saving");
        verify(creationService).saveLocation(l);
        verify(logService).log(eq("alice"), eq(LogType.LOCATION_UPDATED), anyString());
        assertEquals(200, resp.getStatus());
    }

    @Test
    void deleteDelegatesLogsAndReturnsNoContent() {
        loggedInAsAlice();
        Response resp = resource.delete(3L);
        verify(creationService).deleteLocation(3L);
        verify(logService).log(eq("alice"), eq(LogType.LOCATION_DELETED), anyString());
        assertEquals(204, resp.getStatus());
    }
}
