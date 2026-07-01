package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Log;
import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.domain.User;
import de.dhbw.erpbackend.repository.LogRepository;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogResourceTest {

    @Mock LogRepository logRepository;
    @Mock Page<Log> page;
    @InjectMocks LogResource resource;

    private Log log(String username, LogType type, String desc, Instant created) {
        User u = new User();
        u.setUsername(username);
        Log l = new Log();
        l.setUser(u);
        l.setType(type);
        l.setDescription(desc);
        l.setCreated(created);
        return l;
    }

    @Test
    void blankSearchReturnsTotalsAndMapsRows() {
        Log l = log("alice", LogType.USER_LOGIN, "angemeldet", Instant.parse("2026-06-26T10:00:00Z"));
        when(logRepository.countAll()).thenReturn(42L);
        when(logRepository.findAll(any(PageRequest.class), any())).thenReturn(page);
        when(page.content()).thenReturn(List.of(l));

        LogResource.LogPage result = resource.list(7, 0, 25, "");

        assertEquals(7, result.getDraw());
        assertEquals(42L, result.getRecordsTotal());
        assertEquals(42L, result.getRecordsFiltered()); // no filter -> equals total
        assertEquals(1, result.getData().size());
        LogResource.LogRow row = result.getData().getFirst();
        assertEquals("alice", row.getUser());
        assertEquals("USER_LOGIN", row.getType());
        assertEquals("angemeldet", row.getDescription());
        assertFalse(row.getCreated().isBlank(), "timestamp must be formatted");
        verify(logRepository, never()).search(any(), any());
    }

    @Test
    void searchUsesFilteredCount() {
        Log l = log("bob", LogType.PRODUCT_CREATED, "Produkt 'X' wurde erstellt.",
                Instant.parse("2026-06-26T10:00:00Z"));
        when(logRepository.countAll()).thenReturn(42L);
        when(logRepository.search(eq("cola"), any(PageRequest.class))).thenReturn(page);
        when(logRepository.countSearch("cola")).thenReturn(3L);
        when(page.content()).thenReturn(List.of(l));

        LogResource.LogPage result = resource.list(1, 0, 25, "cola");

        assertEquals(42L, result.getRecordsTotal());
        assertEquals(3L, result.getRecordsFiltered());
        assertEquals(1, result.getData().size());
        verify(logRepository, never()).findAll(any(PageRequest.class), any());
    }

    @Test
    void oversizedLengthIsCappedAndPageComputedFromStart() {
        when(logRepository.countAll()).thenReturn(0L);
        when(logRepository.findAll(any(PageRequest.class), any())).thenReturn(page);
        when(page.content()).thenReturn(List.of());

        resource.list(1, 50, 9999, "");

        ArgumentCaptor<PageRequest> pr = ArgumentCaptor.forClass(PageRequest.class);
        verify(logRepository).findAll(pr.capture(), any());
        assertEquals(25, pr.getValue().size(), "oversized length capped to default 25");
        assertEquals(3L, pr.getValue().page(), "start 50 / size 25 = page index 2 -> page 3");
    }
}
