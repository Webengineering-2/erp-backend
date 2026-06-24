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
}
