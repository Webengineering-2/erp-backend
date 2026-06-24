package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.*;
import de.dhbw.erpbackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock ItemRepository itemRepository;
    @Mock ProductRepository productRepository;
    @Mock LocationRepository locationRepository;
    @Mock CustomerRepository customerRepository;

    ItemService service;

    @BeforeEach
    void setUp() {
        service = new ItemService();
        service.itemRepository = itemRepository;
        service.productRepository = productRepository;
        service.locationRepository = locationRepository;
        service.customerRepository = customerRepository;
    }

    private Item stockItem(long id, int qty) {
        Item i = new Item();
        i.setId(id);
        i.setQuantity(qty);
        i.setStatus(ItemStatus.STOCK);
        i.setUnitBuyPrice(new BigDecimal("0.20"));
        i.setProduct(new Product());
        i.setLocation(new Location());
        return i;
    }

    @Test
    void getStockItemsReturnsRepositoryResult() {
        Item i = stockItem(1L, 5);
        when(itemRepository.findByStatus(ItemStatus.STOCK)).thenReturn(List.of(i));

        List<Item> result = service.getStockItems();

        assertEquals(1, result.size());
        assertSame(i, result.get(0));
    }

    @Test
    void getSoldItemsReturnsRepositoryResult() {
        Item i = stockItem(1L, 5);
        i.setStatus(ItemStatus.SOLD);
        when(itemRepository.findByStatusOrderByUpdatedDesc(ItemStatus.SOLD)).thenReturn(List.of(i));

        List<Item> result = service.getSoldItems();

        assertEquals(1, result.size());
        assertSame(i, result.get(0));
    }

    @Test
    void createItemBuildsStockItem() {
        Product p = new Product(); p.setId(7L);
        Location l = new Location(); l.setId(3L);
        when(productRepository.findById(7L)).thenReturn(Optional.of(p));
        when(locationRepository.findById(3L)).thenReturn(Optional.of(l));
        when(itemRepository.insert(any(Item.class))).thenAnswer(inv -> inv.getArgument(0));

        Item created = service.createItem(7L, 3L, 50, new BigDecimal("0.20"));

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).insert(captor.capture());
        Item saved = captor.getValue();
        assertEquals(ItemStatus.STOCK, saved.getStatus());
        assertEquals(50, saved.getQuantity());
        assertEquals(new BigDecimal("0.20"), saved.getUnitBuyPrice());
        assertSame(p, saved.getProduct());
        assertSame(l, saved.getLocation());
        assertSame(saved, created);
    }

    @Test
    void sellFullQuantityWithBuyerFlipsItemToSold() {
        Item item = stockItem(1L, 200);
        Customer buyer = new Customer(); buyer.setId(9L);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(customerRepository.findById(9L)).thenReturn(Optional.of(buyer));

        service.sell(1L, 200, new BigDecimal("0.26"), 9L);

        assertEquals(ItemStatus.SOLD, item.getStatus());
        assertSame(buyer, item.getSoldTo());
        assertEquals(new BigDecimal("0.26"), item.getSellUnitPrice());
        assertEquals(200, item.getQuantity());
        verify(itemRepository).update(item);
        verify(itemRepository, never()).insert(any(Item.class)); // no child created
    }

    @Test
    void sellPartialWithBuyerSplitsAndCreatesSoldChild() {
        Item item = stockItem(1L, 200);
        Customer buyer = new Customer(); buyer.setId(9L);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(customerRepository.findById(9L)).thenReturn(Optional.of(buyer));

        service.sell(1L, 50, new BigDecimal("0.26"), 9L);

        assertEquals(150, item.getQuantity());        // original keeps remainder
        assertEquals(ItemStatus.STOCK, item.getStatus());
        verify(itemRepository).update(item);           // original updated in place

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).insert(captor.capture());
        Item child = captor.getValue();
        assertSame(item, child.getParent());
        assertEquals(50, child.getQuantity());
        assertEquals(ItemStatus.SOLD, child.getStatus());
        assertSame(buyer, child.getSoldTo());
        assertEquals(new BigDecimal("0.26"), child.getSellUnitPrice());
        assertSame(item.getProduct(), child.getProduct());
        assertSame(item.getLocation(), child.getLocation());
        assertEquals(item.getUnitBuyPrice(), child.getUnitBuyPrice());
    }

    @Test
    void sellWithoutBuyerWritesOff() {
        Item item = stockItem(1L, 10);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        service.sell(1L, 10, BigDecimal.ZERO, null);

        assertEquals(ItemStatus.WRITTEN_OFF, item.getStatus());
        assertNull(item.getSoldTo());
    }

    @Test
    void sellRejectsQuantityAboveStock() {
        Item item = stockItem(1L, 5);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThrows(UserFacingException.class,
                () -> service.sell(1L, 6, BigDecimal.ONE, null));
    }

    @Test
    void sellRejectsZeroQuantity() {
        Item item = stockItem(1L, 5);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThrows(UserFacingException.class,
                () -> service.sell(1L, 0, BigDecimal.ONE, null));
    }

    @Test
    void sellRejectsNonStockItem() {
        Item item = stockItem(1L, 5);
        item.setStatus(ItemStatus.SOLD);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThrows(UserFacingException.class,
                () -> service.sell(1L, 1, BigDecimal.ONE, null));
    }

    @Test
    void sellRejectsUnknownItem() {
        when(itemRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserFacingException.class,
                () -> service.sell(99L, 1, BigDecimal.ONE, null));
    }
}
