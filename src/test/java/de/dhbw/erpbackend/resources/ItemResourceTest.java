package de.dhbw.erpbackend.resources;

import de.dhbw.erpbackend.domain.Item;
import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.service.ItemService;
import de.dhbw.erpbackend.service.LogService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemResourceTest {

    @Mock ItemService itemService;
    @Mock LogService logService;
    @Mock HttpServletRequest request;
    @Mock HttpSession session;
    @InjectMocks ItemResource resource;

    @Test
    void createUnpacksItemFieldsDelegatesAndLogs() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("alice");

        Product product = new Product();
        product.setId(7L);
        Location location = new Location();
        location.setId(3L);

        Item item = new Item();
        item.setProduct(product);
        item.setLocation(location);
        item.setQuantity(50);
        item.setUnitBuyPrice(new BigDecimal("0.2000"));

        Response resp = resource.create(item);

        verify(itemService).createItem(7L, 3L, 50, new BigDecimal("0.2000"));
        verify(logService).log(eq("alice"), eq(LogType.ITEM_CREATED), anyString());
        assertEquals(200, resp.getStatus());
    }
}
