package de.dhbw.erpbackend.repository;

import de.dhbw.erpbackend.domain.Category;
import de.dhbw.erpbackend.domain.Customer;
import de.dhbw.erpbackend.domain.Item;
import de.dhbw.erpbackend.domain.ItemStatus;
import de.dhbw.erpbackend.domain.Location;
import de.dhbw.erpbackend.domain.Log;
import de.dhbw.erpbackend.domain.LogType;
import de.dhbw.erpbackend.domain.Product;
import de.dhbw.erpbackend.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

import java.time.Instant;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepositoryIntegrationTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    void setupFactory() {
        Map<String, String> overrides = new HashMap<>();
        overrides.put("jakarta.persistence.jdbc.url", "jdbc:h2:mem:erp-test;DB_CLOSE_DELAY=-1");
        overrides.put("hibernate.hbm2ddl.auto", "create-drop");
        emf = Persistence.createEntityManagerFactory("default", overrides);
    }

    @AfterAll
    void closeFactory() {
        if (emf != null) emf.close();
    }

    @BeforeEach
    void freshEm() {
        if (em != null && em.isOpen()) em.close();
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("delete from Item").executeUpdate();
        em.createQuery("delete from Log").executeUpdate();
        em.createQuery("delete from Product").executeUpdate();
        em.createQuery("delete from Category").executeUpdate();
        em.createQuery("delete from Location").executeUpdate();
        em.createQuery("delete from Customer").executeUpdate();
        em.createQuery("delete from User").executeUpdate();
        em.getTransaction().commit();
    }

    private <T> void persist(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Test
    void connectionAndSchema() {
        assertNotNull(emf);
        assertNotNull(em);
        assertTrue(em.isOpen());
    }

    @Test
    void insertAndQueryUser() {
        User u = new User();
        u.setUsername("alice");
        u.setPasswordHash("hash-1");
        persist(u);

        assertNotNull(u.getId());
        assertNotNull(u.getCreated());
        assertNotNull(u.getUpdated());

        User found = em.createQuery(
                "select u from User u where u.username = :n", User.class)
                .setParameter("n", "alice")
                .getSingleResult();
        assertEquals(u.getId(), found.getId());
        assertEquals("hash-1", found.getPasswordHash());
    }

    @Test
    void productBelongsToCategory() {
        Category cat = new Category();
        cat.setName("Drinks");
        cat.setDescription("Beverages");
        persist(cat);

        Product p = new Product();
        p.setCategory(cat);
        p.setName("Cola 0.5l");
        p.setUnitPrice(new BigDecimal("1.5000"));
        persist(p);

        Product found = em.find(Product.class, p.getId());
        assertEquals("Cola 0.5l", found.getName());
        assertEquals(cat.getId(), found.getCategory().getId());

        List<Product> byCat = em.createQuery(
                "select p from Product p where p.category = :c", Product.class)
                .setParameter("c", cat)
                .getResultList();
        assertEquals(1, byCat.size());
    }

    @Test
    void itemFullGraph() {
        Category cat = new Category();
        cat.setName("Snacks");
        persist(cat);

        Product prod = new Product();
        prod.setCategory(cat);
        prod.setName("Chips");
        prod.setUnitPrice(new BigDecimal("2.0000"));
        persist(prod);

        Location loc = new Location();
        loc.setName("Shelf A");
        persist(loc);

        Item batch = new Item();
        batch.setProduct(prod);
        batch.setQuantity(100);
        batch.setLocation(loc);
        batch.setStatus(ItemStatus.STOCK);
        batch.setUnitBuyPrice(new BigDecimal("1.0000"));
        persist(batch);

        Customer customer = new Customer();
        customer.setName("Bob");
        persist(customer);

        Item sold = new Item();
        sold.setProduct(prod);
        sold.setQuantity(50);
        sold.setLocation(loc);
        sold.setStatus(ItemStatus.SOLD);
        sold.setSoldTo(customer);
        sold.setSellUnitPrice(new BigDecimal("2.5000"));
        sold.setUnitBuyPrice(new BigDecimal("1.0000"));
        sold.setParent(batch);
        persist(sold);

        Item foundSold = em.find(Item.class, sold.getId());
        assertEquals(ItemStatus.SOLD, foundSold.getStatus());
        assertEquals(batch.getId(), foundSold.getParent().getId());
        assertEquals(customer.getId(), foundSold.getSoldTo().getId());
        assertEquals(prod.getId(), foundSold.getProduct().getId());
        assertEquals(loc.getId(), foundSold.getLocation().getId());

        List<Item> sales = em.createQuery(
                "select i from Item i where i.status = :s", Item.class)
                .setParameter("s", ItemStatus.SOLD)
                .getResultList();
        assertEquals(1, sales.size());
    }

    @Test
    void timestampsAreSetViaStatelessSession() {
        // Jakarta Data repositories use a StatelessSession, which does NOT fire
        // JPA @PrePersist/@PreUpdate. This guards that created/updated are still
        // populated (via Hibernate's @CreationTimestamp/@UpdateTimestamp) on that path.
        Category cat = new Category();
        cat.setName("Stateless");

        SessionFactory sf = emf.unwrap(SessionFactory.class);
        try (StatelessSession ss = sf.openStatelessSession()) {
            ss.getTransaction().begin();
            ss.insert(cat);
            ss.getTransaction().commit();
        }

        assertNotNull(cat.getId());
        Category found = em.find(Category.class, cat.getId());
        assertNotNull(found.getCreated(), "created must be set on stateless insert");
        assertNotNull(found.getUpdated(), "updated must be set on stateless insert");
    }

    @Test
    void logRelatesToUser() {
        User u = new User();
        u.setUsername("carol");
        u.setPasswordHash("hash-2");
        persist(u);

        Log log = new Log();
        log.setUser(u);
        log.setType(LogType.USER_LOGIN);
        log.setDescription("login from 127.0.0.1");
        persist(log);

        List<Log> logs = em.createQuery(
                "select l from Log l where l.user = :u", Log.class)
                .setParameter("u", u)
                .getResultList();
        assertEquals(1, logs.size());
        assertEquals(LogType.USER_LOGIN, logs.getFirst().getType());
        assertEquals(u.getId(), logs.getFirst().getUser().getId());
    }

    // ---------------------------------------------------------------------
    // Generated-repository query behaviour (exercised through a StatelessSession,
    // exactly as the deployed app does — not via the EntityManager above).
    // ---------------------------------------------------------------------

    /** Runs read-only repository work in a StatelessSession (no transaction). */
    private <T> T inStateless(Function<StatelessSession, T> work) {
        try (StatelessSession ss = emf.unwrap(SessionFactory.class).openStatelessSession()) {
            return work.apply(ss);
        }
    }

    /** Runs mutating repository work in a transactional StatelessSession. */
    private void inStatelessTx(Consumer<StatelessSession> work) {
        try (StatelessSession ss = emf.unwrap(SessionFactory.class).openStatelessSession()) {
            ss.getTransaction().begin();
            work.accept(ss);
            ss.getTransaction().commit();
        }
    }

    private Product seedProduct(String name, Category category) {
        Product p = new Product();
        p.setName(name);
        p.setCategory(category);
        p.setUnitPrice(new BigDecimal("1.0000"));
        persist(p);
        return p;
    }

    private Item seedItem(Product product, Location location, ItemStatus status, int qty) {
        Item i = new Item();
        i.setProduct(product);
        i.setLocation(location);
        i.setStatus(status);
        i.setQuantity(qty);
        i.setUnitBuyPrice(new BigDecimal("1.0000"));
        persist(i);
        return i;
    }

    private void setUpdated(Long itemId, Instant instant) {
        // Bulk HQL update does NOT trigger @UpdateTimestamp, so we can pin a value.
        em.getTransaction().begin();
        em.createQuery("update Item set updated = :t where id = :id")
                .setParameter("t", instant)
                .setParameter("id", itemId)
                .executeUpdate();
        em.getTransaction().commit();
        em.clear();
    }

    @Test
    void findByNameLikeIsCaseInsensitiveSubstring() {
        Category cat = new Category();
        cat.setName("Drinks");
        persist(cat);
        seedProduct("Cola Zero", cat);
        seedProduct("Fanta", cat);

        List<Product> ola = inStateless(ss -> new ProductRepository_(ss).findByNameLike("ola"));
        assertEquals(1, ola.size());
        assertEquals("Cola Zero", ola.getFirst().getName());

        // Case-insensitive (ilike): uppercase query still matches.
        assertEquals(1, inStateless(ss -> new ProductRepository_(ss).findByNameLike("COLA")).size());
        // Substring matching both rows.
        assertEquals(2, inStateless(ss -> new ProductRepository_(ss).findByNameLike("a")).size());
        // No match.
        assertTrue(inStateless(ss -> new ProductRepository_(ss).findByNameLike("xyz")).isEmpty());
    }

    @Test
    void findByCategoryFiltersByCategory() {
        Category drinks = new Category();
        drinks.setName("Drinks");
        persist(drinks);
        Category snacks = new Category();
        snacks.setName("Snacks");
        persist(snacks);

        seedProduct("Cola", drinks);
        seedProduct("Water", drinks);
        seedProduct("Chips", snacks);

        assertEquals(2, inStateless(ss -> new ProductRepository_(ss).findByCategory(drinks)).size());
        assertEquals(1, inStateless(ss -> new ProductRepository_(ss).findByCategory(snacks)).size());
    }

    @Test
    void findByStatusFiltersByStatus() {
        Category cat = new Category();
        cat.setName("c");
        persist(cat);
        Product p = seedProduct("p", cat);
        Location loc = new Location();
        loc.setName("loc");
        persist(loc);

        seedItem(p, loc, ItemStatus.STOCK, 10);
        seedItem(p, loc, ItemStatus.SOLD, 5);
        seedItem(p, loc, ItemStatus.SOLD, 3);

        assertEquals(1, inStateless(ss -> new ItemRepository_(ss).findByStatus(ItemStatus.STOCK)).size());
        assertEquals(2, inStateless(ss -> new ItemRepository_(ss).findByStatus(ItemStatus.SOLD)).size());
    }

    @Test
    void findByStatusOrderByUpdatedDescReturnsNewestFirst() {
        Category cat = new Category();
        cat.setName("c");
        persist(cat);
        Product p = seedProduct("p", cat);
        Location loc = new Location();
        loc.setName("loc");
        persist(loc);

        Item oldest = seedItem(p, loc, ItemStatus.SOLD, 1);
        Item middle = seedItem(p, loc, ItemStatus.SOLD, 2);
        Item newest = seedItem(p, loc, ItemStatus.SOLD, 3);
        // A STOCK item that must be excluded regardless of its (newer) timestamp.
        Item stock = seedItem(p, loc, ItemStatus.STOCK, 9);

        setUpdated(oldest.getId(), Instant.parse("2024-01-01T00:00:00Z"));
        setUpdated(middle.getId(), Instant.parse("2024-06-01T00:00:00Z"));
        setUpdated(newest.getId(), Instant.parse("2024-12-01T00:00:00Z"));
        setUpdated(stock.getId(), Instant.parse("2025-12-01T00:00:00Z"));

        List<Item> sold = inStateless(ss ->
                new ItemRepository_(ss).findByStatusOrderByUpdatedDesc(ItemStatus.SOLD));

        assertEquals(3, sold.size());
        assertEquals(newest.getId(), sold.get(0).getId());
        assertEquals(middle.getId(), sold.get(1).getId());
        assertEquals(oldest.getId(), sold.get(2).getId());
    }

    @Test
    void repositoryInsertSetsCreatedAndUpdated() {
        Category cat = new Category();
        cat.setName("c");
        persist(cat);
        Product p = seedProduct("p", cat);
        Location loc = new Location();
        loc.setName("loc");
        persist(loc);

        Item item = new Item();
        item.setProduct(p);
        item.setLocation(loc);
        item.setStatus(ItemStatus.STOCK);
        item.setQuantity(42);
        item.setUnitBuyPrice(new BigDecimal("1.0000"));

        inStatelessTx(ss -> new ItemRepository_(ss).insert(item));

        assertNotNull(item.getId());
        em.clear();
        Item found = em.find(Item.class, item.getId());
        assertNotNull(found.getCreated(), "created must be set on repository insert");
        assertNotNull(found.getUpdated(), "updated must be set on repository insert");
    }

    @Test
    void repositoryUpdateRefreshesUpdatedButKeepsCreated() {
        Category cat = new Category();
        cat.setName("c");
        persist(cat);
        Product p = seedProduct("p", cat);
        Location loc = new Location();
        loc.setName("loc");
        persist(loc);

        Item item = new Item();
        item.setProduct(p);
        item.setLocation(loc);
        item.setStatus(ItemStatus.STOCK);
        item.setQuantity(10);
        item.setUnitBuyPrice(new BigDecimal("1.0000"));
        inStatelessTx(ss -> new ItemRepository_(ss).insert(item));

        em.clear();
        Item afterInsert = em.find(Item.class, item.getId());
        Instant created = afterInsert.getCreated();
        Instant firstUpdated = afterInsert.getUpdated();
        assertNotNull(created);
        assertNotNull(firstUpdated);
        em.clear(); // detach so the bulk-update / stateless write are the only writers

        // Force `updated` backwards to an old value; the @UpdateTimestamp generator
        // must overwrite it with a fresh value on update.
        afterInsert.setUpdated(Instant.EPOCH);
        afterInsert.setQuantity(123);
        inStatelessTx(ss -> new ItemRepository_(ss).update(afterInsert));

        em.clear();
        Item afterUpdate = em.find(Item.class, item.getId());
        assertEquals(123, afterUpdate.getQuantity(), "field change must persist");
        assertEquals(created, afterUpdate.getCreated(), "created must NOT change on update");
        assertFalse(afterUpdate.getUpdated().equals(Instant.EPOCH),
                "updated must be refreshed by the generator, not kept at the forced value");
        assertTrue(afterUpdate.getUpdated().isAfter(Instant.EPOCH));
    }
}
