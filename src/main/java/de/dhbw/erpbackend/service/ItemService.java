package de.dhbw.erpbackend.service;

import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.Item;
import de.dhbw.erpbackend.domain.ItemStatus;
import de.dhbw.erpbackend.repository.CustomerRepository;
import de.dhbw.erpbackend.repository.ItemRepository;
import de.dhbw.erpbackend.repository.LocationRepository;
import de.dhbw.erpbackend.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Named
@ApplicationScoped
@Transactional
public class ItemService {

    // Package-private for unit-test field injection
    @Inject ItemRepository itemRepository;
    @Inject ProductRepository productRepository;
    @Inject LocationRepository locationRepository;
    @Inject CustomerRepository customerRepository;

    public List<Item> getStockItems() {
        return itemRepository.findByStatus(ItemStatus.STOCK);
    }

    public Item createItem(Long productId, Long locationId, int quantity, BigDecimal buyPrice) {
        Item item = new Item();
        item.setProduct(productRepository.findById(productId)
                .orElseThrow(() -> new UserFacingException("Produkt nicht gefunden.")));
        item.setLocation(locationRepository.findById(locationId)
                .orElseThrow(() -> new UserFacingException("Lagerort nicht gefunden.")));
        item.setQuantity(quantity);
        item.setUnitBuyPrice(buyPrice);
        item.setStatus(ItemStatus.STOCK);
        return itemRepository.insert(item);
    }

    public void sell(Long itemId, int quantity, BigDecimal sellUnitPrice, Long customerId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new UserFacingException("Artikel nicht gefunden."));

        if (item.getStatus() != ItemStatus.STOCK) {
            throw new UserFacingException("Dieser Artikel ist nicht auf Lager.");
        }
        if (quantity < 1 || quantity > item.getQuantity()) {
            throw new UserFacingException("Die angegebene Menge ist ungültig.");
        }

        Customer buyer = null;
        if (customerId != null) {
            buyer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new UserFacingException("Käufer nicht gefunden."));
        }
        ItemStatus newStatus = buyer != null ? ItemStatus.SOLD : ItemStatus.WRITTEN_OFF;

        if (quantity == item.getQuantity()) {
            item.setStatus(newStatus);
            item.setSoldTo(buyer);
            item.setSellUnitPrice(sellUnitPrice);
            itemRepository.update(item);
        } else {
            item.setQuantity(item.getQuantity() - quantity);
            itemRepository.update(item);

            Item sold = new Item();
            sold.setParent(item);
            sold.setProduct(item.getProduct());
            sold.setLocation(item.getLocation());
            sold.setUnitBuyPrice(item.getUnitBuyPrice());
            sold.setQuantity(quantity);
            sold.setStatus(newStatus);
            sold.setSoldTo(buyer);
            sold.setSellUnitPrice(sellUnitPrice);
            itemRepository.insert(sold);
        }
    }
}
