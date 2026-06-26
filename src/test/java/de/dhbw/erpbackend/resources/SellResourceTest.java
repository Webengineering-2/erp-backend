package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.ItemStatus;
import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.service.ItemService;
import de.dhbw.erpbackend.service.LogService;
import de.dhbw.erpbackend.service.UserFacingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellResourceTest {

    @Mock ItemService itemService;
    @Mock LogService logService;
    @Mock HttpServletRequest request;
    @Mock HttpSession session;
    @InjectMocks SellResource resource;

    private SellRequest request(int qty, BigDecimal price, Long customerId) {
        SellRequest r = new SellRequest();
        r.setQuantity(qty);
        r.setSellUnitPrice(price);
        r.setCustomerId(customerId);
        return r;
    }

    private void loggedInAsAlice() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");
    }

    @Test
    void soldDispositionLogsItemSold() {
        loggedInAsAlice();
        when(itemService.sell(1L, 5, new BigDecimal("0.2600"), 9L)).thenReturn(ItemStatus.SOLD);

        Response resp = resource.sell(1L, request(5, new BigDecimal("0.2600"), 9L));

        verify(logService).log(eq("alice"), eq(LogType.ITEM_SOLD), anyString());
        assertEquals(200, resp.getStatus());
    }

    @Test
    void writtenOffDispositionLogsItemWrittenOff() {
        loggedInAsAlice();
        when(itemService.sell(1L, 5, new BigDecimal("0.0000"), null)).thenReturn(ItemStatus.WRITTEN_OFF);

        Response resp = resource.sell(1L, request(5, new BigDecimal("0.0000"), null));

        verify(logService).log(eq("alice"), eq(LogType.ITEM_WRITTEN_OFF), anyString());
        assertEquals(200, resp.getStatus());
    }

    @Test
    void sellMapsUserFacingExceptionToBadRequestWithMessageAndDoesNotLog() {
        doThrow(new UserFacingException("Dieser Artikel ist nicht auf Lager."))
                .when(itemService).sell(1L, 5, new BigDecimal("0.2600"), null);

        Response resp = resource.sell(1L, request(5, new BigDecimal("0.2600"), null));

        assertEquals(400, resp.getStatus());
        assertEquals("Dieser Artikel ist nicht auf Lager.", resp.getEntity());
        verify(logService, never()).log(anyString(), org.mockito.ArgumentMatchers.any(), anyString());
    }
}
