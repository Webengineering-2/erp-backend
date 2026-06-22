# erp-backend — project notes

Jakarta EE 10 ERP backend. Java 25, Gradle 9.5, Apache TomEE Embedded 10.1.5, Hibernate 7, JSP for views, H2 for storage. Issue tracker on GitHub at `Webengineering-2/erp-backend`; scratch notes / Excalidraw diagram at `Webengineering-2/erp-scratch-notes`.

## Run / test

```bash
./gradlew run     # builds the war, boots embedded TomEE on :8080
./gradlew test    # JUnit 5 + Mockito
```

Server is at `http://localhost:8080/`. Override port with `SERVER_PORT=9090`.

The H2 database lives at `./data/erp.*`. **Do not delete `data/`** — it holds the developer's working state. Log in with the existing dev account `admin` / `123` instead of wiping and re-onboarding.

**JVM:** Pinned to JDK 25 via toolchain. 
## Architecture

Standard Jakarta EE servlet MVC. The flow is `Servlet → @ApplicationScoped service → Jakarta Data repository → JPA entity`.

```
src/main/java/de/dhbw/erpbackend/
  domain/        JPA entities + enums + BaseEntity (id, created, updated)
  repository/    Jakarta Data @Repository interfaces (Hibernate generates the impl)
  service/       @ApplicationScoped CDI beans, @Transactional methods
  web/           @WebServlet servlets, JSP forwards
  HelloApplication / HelloResource    JAX-RS sample, served at /api/*
src/main/webapp/                 page JSPs, served directly (login, onboarding, overview)
src/main/webapp/create/          one page per entity (products, categories, locations, customers)
src/main/webapp/WEB-INF/components/  <jsp:include> fragments (header, sidebar, dialogs)
src/main/webapp/WEB-INF/tags/    JSP tag files (auth guards)
src/main/webapp/WEB-INF/views/   error.jsp only (container error target, not navigable)
src/devServer/java/...DevServer.java  Embedded TomEE launcher (`./gradlew run`)
```

### Servlets and routing

**View philosophy:** Page JSPs live in `webapp/` and are reached directly by URL. They pull their own data by calling `@Named` services from EL (`${creationService.getMatchingProducts(param.search)}`) — no servlet sets request attributes for rendering. Display logic (search filtering, list rendering) lives in the JSP via JSTL. Servlets remain only for side-effecting controller actions (authenticate, register, logout) and root routing.

**One page per view:** the create/management area is one JSP per entity under `webapp/create/` — `products.jsp` (the default), `categories.jsp`, `locations.jsp`, `customers.jsp`. Each is its own URL, calls only its own service method, and includes only its own create/edit dialogs (plus the shared `deleteEntity` dialog, header, sidebar). There is no `createView` dispatch param — the old single `create.jsp` that conditionally rendered all four views was split to fix exactly that. The search/table markup is intentionally duplicated across the four pages rather than abstracted.

- `RootServlet` (`""`) — routes `/` based on session + DB state:
  - logged in → `/overview.jsp`
  - no user in DB → `/onboarding.jsp`
  - else → `/login.jsp`
- `OnboardingServlet` — POST only: creates first user and logs in, redirects to `/overview.jsp`. The form is `onboarding.jsp` (served directly).
- `LoginServlet` — POST only: authenticates via `LoginService` and logs in, redirects to `/overview.jsp`. The form is `login.jsp` (served directly).
- `LogoutServlet` — `GET /logout`, invalidates session, redirects to `/`.

(`OverviewServlet` and `CreateServlet` were removed — `overview.jsp` and the `create/*.jsp` pages are served directly and call services themselves.)

### Auth: JSP tag files (not a servlet base class)

Since page JSPs sit in `webapp/` they aren't protected by a servlet, so guards live in tag files under `WEB-INF/tags/`, included at the top of each page:

- `<auth:requireLogin/>` — protected pages (`overview.jsp`, all `create/*.jsp`): redirects to `/` when no user in session.
- `<auth:redirectIfLoggedIn/>` — public pages (`login.jsp`, `onboarding.jsp`): redirects to `/overview.jsp` when already logged in.

Both use `<c:redirect>` (which throws `SkipPageException`, aborting the rest of the page). Declare with `<%@ taglib prefix="auth" tagdir="/WEB-INF/tags" %>`. **Every new protected page in `webapp/` must start with `<auth:requireLogin/>`** — the guard is opt-in per page.

### `BaseServlet` / `ProtectedServlet`

`BaseServlet` overrides `service()` to wrap `super.service()` in a try/catch for `UserFacingException` → `ErrorHelper.showError`. Application servlets extend it, so `doGet`/`doPost` just *throw* `UserFacingException` — no local try/catch needed.

`ProtectedServlet extends BaseServlet` adds a session check (redirect to `/` if not logged in). It is **currently unused** — page auth now lives in the `requireLogin` tag — but kept for any future authenticated servlet endpoint.

### `SessionHelper`

Session attribute is `username` (String). Helpers: `isLoggedIn`, `currentUsername`, `login(req, username)`, `logout(req)`.

### Error handling

- `UserFacingException(message)` — `RuntimeException` whose `getMessage()` is shown to the user (German, full sentences).
- Servlet pattern: just *throw* `UserFacingException` from `doGet`/`doPost`. `BaseServlet.service()` catches it and calls `ErrorHelper.showError(req, resp, ex)` — this sets `errorMessage` attribute, sets status 400, forwards to `/WEB-INF/views/error.jsp`.
- `web.xml` maps `<exception-type>java.lang.Throwable</exception-type>` to the same `error.jsp` as a fallback for anything else that escapes.
- error.jsp renders `${errorMessage}` if present, otherwise a generic message; "OK" link goes to `/`.

### Transactions

`OnboardingService.register` is annotated `@jakarta.transaction.Transactional` — TomEE provides JTA so this just works. Don't roll your own tx management; just slap `@Transactional` on a service method.

### Password hashing

`PasswordService` wraps BCrypt (`at.favre.lib:bcrypt`). Cost factor 12. `hash(plain)` and `verify(plain, hash)`. Never store plaintext, never log the hash.

## Persistence

### Entities

Defined in `domain/`, all extend `BaseEntity` (Long id IDENTITY, Instant created, Instant updated; `@PrePersist` / `@PreUpdate` set these automatically). Tables follow the Excalidraw diagram in `erp-scratch-notes`. Money columns are `BigDecimal(19, 4)`. The diagram's "Entity" is named `Item` in code to avoid colliding with `@jakarta.persistence.Entity`.

Existing entities: `User`, `Log`, `Category`, `Product`, `Location`, `Customer`, `Item` (with `ItemStatus`, `LogType` enums).

### Repositories

Jakarta Data interfaces extending `CrudRepository<T, Long>`. Hibernate's annotation processor generates the `_Impl` classes at compile time. They run against a `StatelessSession` and use TomEE's JTA transaction manager.

Convention: keep methods minimal — `@Find` for finders, `@Query("…")` for non-trivial queries.

### `persistence.xml`

- `src/main/resources/META-INF/persistence.xml` — **JTA**, `<jta-data-source>erpDataSource</jta-data-source>`. The container provides the data source (defined programmatically in `DevServer`). This is the production-style config.
- `src/test/resources/META-INF/persistence.xml` — **RESOURCE_LOCAL** with an in-memory H2 URL, no JTA. Tests use `Persistence.createEntityManagerFactory("default", overrides)` directly. The test resource overrides the main one on the test classpath.

## TomEE Embedded dev server

`./gradlew run` invokes `de.dhbw.erpbackend.devserver.DevServer` (lives in the `devServer` source set so it can have its own classpath without polluting the war's WEB-INF/lib).

The DevServer:
1. Builds a TomEE `Configuration` with the HTTP port and a programmatic `erpDataSource` Resource (H2, JTA-managed).
2. `container.setup(cfg)` → `container.start()` → `container.deploy("", war, true)`.
3. `container.await()` blocks forever; shutdown hook calls `container.stop()`.

### Classpath split (important!)

The classloader hierarchy mirrors a real TomEE install (`apache-tomee/lib/` vs `webapp/WEB-INF/lib/`). Putting things in the wrong place breaks the JPA metamodel or the JTA bridge.

**Container classpath** (`devServerImplementation` — analogous to `apache-tomee/lib/`):
- `tomee-embedded` (the container itself)
- `hibernate-core` (the JPA provider TomEE will load)
- `openejb-jpa-integration` (bridges Hibernate's JtaPlatform SPI to TomEE's TransactionManager — without this Hibernate can't find `OpenEJBJtaPlatform2`)
- `h2` (so TomEE can create the DataSource at container startup)

**War (WEB-INF/lib)** (`implementation`):
- `jakarta.data-api` (compile-time API; Hibernate's generator produces the impl as part of our classes)
- `jstl-api` + `jstl` impl
- `h2` (also here so the webapp can talk to it directly)
- `bcrypt`

**compileOnly** (provided by the container, not packaged):
- All Jakarta EE APIs (`jakarta.servlet-api`, `jakarta.servlet.jsp-api`, `jakarta.enterprise.cdi-api`, `jakarta.transaction-api`, `jakarta.ws.rs-api`, `jakarta.annotation-api`)
- `hibernate-core` (loaded from the container CL at runtime)

Why both sides of Hibernate (`compileOnly` for our code, `devServerImplementation` for the container): the container CL needs the actual jar at runtime; our code only needs the API for compilation, and we *must not* put Hibernate in WEB-INF/lib or we get two copies of `User.class`/etc. through different ClassLoaders → JPA metamodel identity errors.

`tomee-webservices` is excluded transitively — it pulls Shibboleth opensaml jars not on Maven Central.

## JSP / JSTL

JSPs use JSTL 3.0 (jakarta namespace) with the URI `jakarta.tags.core`.

**Workaround currently in place:** TomEE Embedded's Jasper doesn't auto-discover the JSTL URI from `WEB-INF/lib/jakarta.servlet.jsp.jstl-3.0.1.jar` in our setup. `web.xml` has an explicit `<jsp-config><taglib>` mapping the URI to `/WEB-INF/tlds/c.tld` (a copy of the TLD extracted from the JSTL jar).

If we need more JSTL libraries (`fn:`, `fmt:`), extract their TLDs the same way and add `<taglib>` entries in `web.xml`. Better long-term fix would be to figure out the `JarScanFilter` config — not done yet.

## Testing

- `RepositoryIntegrationTest` — boots Hibernate with the test resource-local persistence-unit against in-memory H2; exercises schema + relations + CRUD via `EntityManager`.
- `OnboardingServiceTest` — pure unit test with **Mockito** for `UserRepository`. We don't spin up a CDI container per test (too heavy); business logic is tested in isolation. The persistence layer is covered by the integration test.
- `PasswordServiceTest` — exercises hash/verify with real BCrypt.

When you add a new service method, follow the Mockito pattern. When you add a new entity, add a smoke test in `RepositoryIntegrationTest` for relations.

## Git workflow / collaboration

- **Don't push automatically** — the user often layers multiple related commits onto a single feature branch in one session. Commit locally if asked for - otherwise the user will comit on their own.
- One branch per issue; branch off `main`.
- PRs reference the issue number. Issues live on GitHub at `Webengineering-2/erp-backend`.

## Useful gotchas / history

- **Orphan `java.exe`**: killing `./gradlew run` doesn't always kill the JavaExec-spawned DevServer JVM. If `:run` fails with `BindException: Address already in use`, kill leftover `java.exe` processes (PowerShell: `Get-Process java | Stop-Process -Force`) and try again.
