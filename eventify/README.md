# Eventify - Panel Administrativo (Thymeleaf + MVC)

Implementación de la HU: interfaz web para coordinadores de eventos, separada
de la API REST, siguiendo el patrón Post-Redirect-Get.

## Cómo ejecutar

```bash
mvn spring-boot:run
```

La app arranca en `http://localhost:8080`.

- Panel web: `http://localhost:8080/admin/events` y `http://localhost:8080/admin/venues`
- API REST: `http://localhost:8080/api/events` y `http://localhost:8080/api/venues`
- Swagger (solo documenta `/api/**`): `http://localhost:8080/swagger-ui.html`
- Consola H2 (para ver la BD): `http://localhost:8080/h2-console`
  (JDBC URL: `jdbc:h2:file:./data/eventifydb`, user: `sa`, sin password)

Al arrancar se siembran 2 lugares de ejemplo (`DataSeeder`) para poder probar
el formulario de eventos sin tener que crear un lugar primero.

## Cómo correr los tests

```bash
mvn test
```

Incluye tests con `MockMvc` que verifican, para las rutas `/admin/events` y
`/admin/venues`: status 200, nombre de vista correcto y presencia de los
atributos esperados en el `Model` (Escenario 4), además de un test de
redirección tras un `POST` exitoso (Escenario 3).

## Mapeo con la HU

| Requisito | Dónde está |
|---|---|
| `th:each`, `th:text` | `templates/events/list.html`, `templates/venues/list.html` |
| `th:if` / `th:unless` (catálogo vacío) | mismas vistas de listado |
| Formularios `th:action` / `th:object` | `templates/events/form.html`, `templates/venues/form.html` |
| Controladores `@Controller` + `Model` | `controller/web/EventViewController.java`, `VenueViewController.java` |
| `@ModelAttribute` en POST | mismos controladores de vista |
| Redirección Post-Redirect-Get | `return "redirect:/admin/..."` tras guardar |
| Separación `/api` vs `/admin` | `controller/api/*` vs `controller/web/*` |
| Swagger solo en `/api` | `springdoc.pathsToMatch=/api/**` en `application.properties` |
| Tests MockMvc | `src/test/java/com/eventify/controller/web/*Test.java` |

## Notas

- La entidad `Event` tiene una relación `@ManyToOne` a `Venue`. El
  `VenueIdToVenueConverter` permite que el `<select>` del formulario envíe
  solo el `id` del lugar y Spring lo convierta automáticamente al objeto
  `Venue` al hacer el binding con `@ModelAttribute`.
- Se usa H2 en modo archivo (no en memoria) para que los datos persistan
  entre reinicios, tal como pide el escenario de guardado exitoso.
