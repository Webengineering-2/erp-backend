package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.service.ItemService;
import de.dhbw.erpbackend.service.UserFacingException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SellResourceTest {

    @Mock ItemService itemService;
    @InjectMocks SellResource resource;

    private SellRequest request(int qty, BigDecimal price, Long customerId) {
        SellRequest r = new SellRequest();
        r.setQuantity(qty);
        r.setSellUnitPrice(price);
        r.setCustomerId(customerId);
        return r;
    }

    @Test
    void sellSuccessDelegatesAndReturnsOk() {
        Response resp = resource.sell(1L, request(5, new BigDecimal("0.2600"), 9L));

        verify(itemService).sell(1L, 5, new BigDecimal("0.2600"), 9L);
        assertEquals(200, resp.getStatus());
    }

    @Test
    void sellMapsUserFacingExceptionToBadRequestWithMessage() {
        doThrow(new UserFacingException("Dieser Artikel ist nicht auf Lager."))
                .when(itemService).sell(1L, 5, new BigDecimal("0.2600"), null);

        Response resp = resource.sell(1L, request(5, new BigDecimal("0.2600"), null));

        assertEquals(400, resp.getStatus());
        assertEquals("Dieser Artikel ist nicht auf Lager.", resp.getEntity());
    }
}
