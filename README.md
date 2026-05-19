# Sistema de Gestión de Biblioteca — API REST

API REST construida con **Spring Boot 3.3** y **MongoDB Atlas** para el curso de Diseño de Software — 4° Semestre.

---

## Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje de programación |
| Spring Boot | 3.3.0 | Framework principal |
| Spring Data MongoDB | 3.3.0 | Integración con MongoDB |
| Spring Validation | 3.3.0 | Validación de datos de entrada |
| Lombok | Latest | Reducción de código repetitivo |
| MongoDB Atlas | M0 (Free) | Base de datos en la nube |

---

## Arquitectura por Capas

```
Cliente (Postman)
      │
      ▼ HTTP Request (JSON)
┌─────────────────────┐
│   CONTROLLER        │  Recibe peticiones HTTP, delega al Service
├─────────────────────┤
│   DTO               │  Define la forma de los datos de entrada/salida
├─────────────────────┤
│   SERVICE           │  Contiene la lógica de negocio (interfaz + impl)
├─────────────────────┤
│   REPOSITORY        │  Acceso a MongoDB (generado automáticamente)
├─────────────────────┤
│   MODEL             │  Entidades mapeadas a documentos MongoDB
└─────────────────────┘
      │
      ▼
  MongoDB Atlas
```

---

## Estructura del Proyecto

```
biblioteca-api/
└── src/
    ├── main/
    │   ├── java/com/biblioteca/
    │   │   ├── BibliotecaApiApplication.java   ← Clase principal
    │   │   ├── controller/
    │   │   │   ├── LibroController.java
    │   │   │   ├── UsuarioController.java
    │   │   │   ├── EjemplarController.java
    │   │   │   └── PrestamoController.java
    │   │   ├── dto/
    │   │   │   ├── LibroRequest.java / LibroResponse.java
    │   │   │   ├── UsuarioRequest.java / UsuarioResponse.java
    │   │   │   ├── EjemplarRequest.java / EjemplarResponse.java
    │   │   │   └── PrestamoRequest.java / PrestamoResponse.java
    │   │   ├── model/
    │   │   │   ├── Libro.java
    │   │   │   ├── Usuario.java
    │   │   │   ├── Ejemplar.java
    │   │   │   └── Prestamo.java
    │   │   ├── repository/
    │   │   │   ├── LibroRepository.java
    │   │   │   ├── UsuarioRepository.java
    │   │   │   ├── EjemplarRepository.java
    │   │   │   └── PrestamoRepository.java
    │   │   ├── service/
    │   │   │   ├── LibroService.java
    │   │   │   ├── UsuarioService.java
    │   │   │   ├── EjemplarService.java
    │   │   │   ├── PrestamoService.java
    │   │   │   └── impl/
    │   │   │       ├── LibroServiceImpl.java
    │   │   │       ├── UsuarioServiceImpl.java
    │   │   │       ├── EjemplarServiceImpl.java
    │   │   │       └── PrestamoServiceImpl.java
    │   │   └── exception/
    │   │       └── GlobalExceptionHandler.java
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/com/biblioteca/
            └── BibliotecaApiApplicationTests.java
```

---

## Configuración

### 1. Configurar MongoDB Atlas

Edita `src/main/resources/application.properties` y reemplaza la URI:

```properties
spring.data.mongodb.uri=mongodb+srv://<usuario>:<password>@<cluster>.mongodb.net/biblioteca_db?retryWrites=true&w=majority
spring.data.mongodb.database=biblioteca_db
```

### 2. Ejecutar la aplicación

```bash
# Con Maven
mvn spring-boot:run

# O desde IntelliJ IDEA: clic derecho en BibliotecaApiApplication → Run
```

La API estará disponible en: `http://localhost:8080`

---

## Endpoints de la API

### 📚 Libros — `/api/libros`

| Método | URL | Descripción | Código |
|---|---|---|---|
| `POST` | `/api/libros` | Crear un libro | 201 |
| `GET` | `/api/libros` | Listar todos los libros | 200 |
| `GET` | `/api/libros/{id}` | Consultar un libro | 200 |
| `PUT` | `/api/libros/{id}` | Actualizar un libro | 200 |
| `DELETE` | `/api/libros/{id}` | Eliminar un libro | 204 |
| `GET` | `/api/libros/buscar/titulo?q=` | Buscar por título | 200 |
| `GET` | `/api/libros/buscar/autor?q=` | Buscar por autor | 200 |
| `GET` | `/api/libros/buscar/categoria?q=` | Buscar por categoría | 200 |

### 👤 Usuarios — `/api/usuarios`

| Método | URL | Descripción | Código |
|---|---|---|---|
| `POST` | `/api/usuarios` | Registrar un usuario | 201 |
| `GET` | `/api/usuarios` | Listar todos los usuarios | 200 |
| `GET` | `/api/usuarios/{id}` | Consultar un usuario | 200 |
| `PUT` | `/api/usuarios/{id}` | Actualizar un usuario | 200 |
| `DELETE` | `/api/usuarios/{id}` | Eliminar un usuario | 204 |
| `GET` | `/api/usuarios/tipo/{tipo}` | Listar por tipo | 200 |
| `GET` | `/api/usuarios/buscar?nombre=` | Buscar por nombre | 200 |

### 📖 Ejemplares — `/api/ejemplares`

| Método | URL | Descripción | Código |
|---|---|---|---|
| `POST` | `/api/ejemplares` | Registrar un ejemplar | 201 |
| `GET` | `/api/ejemplares` | Listar todos los ejemplares | 200 |
| `GET` | `/api/ejemplares/{id}` | Consultar un ejemplar | 200 |
| `PUT` | `/api/ejemplares/{id}` | Actualizar un ejemplar | 200 |
| `DELETE` | `/api/ejemplares/{id}` | Eliminar un ejemplar | 204 |
| `GET` | `/api/ejemplares/libro/{libroId}` | Ejemplares de un libro | 200 |
| `GET` | `/api/ejemplares/libro/{libroId}/disponibles` | Disponibles de un libro | 200 |
| `GET` | `/api/ejemplares/estado/{estado}` | Listar por estado | 200 |

### 🔄 Préstamos — `/api/prestamos`

| Método | URL | Descripción | Código |
|---|---|---|---|
| `POST` | `/api/prestamos` | Registrar un préstamo | 201 |
| `PUT` | `/api/prestamos/{id}/devolucion` | Registrar devolución | 200 |
| `GET` | `/api/prestamos` | Listar todos los préstamos | 200 |
| `GET` | `/api/prestamos/{id}` | Consultar un préstamo | 200 |
| `GET` | `/api/prestamos/activos` | Listar préstamos activos | 200 |
| `GET` | `/api/prestamos/vencidos` | Listar préstamos vencidos | 200 |
| `GET` | `/api/prestamos/usuario/{usuarioId}` | Préstamos de un usuario | 200 |

---

## Ejemplos de uso con Postman

### Crear un libro
```
POST http://localhost:8080/api/libros
Content-Type: application/json

{
  "isbn": "978-0-13-468599-1",
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "anioPublicacion": 2008,
  "categoria": "Programación",
  "descripcion": "Guía para escribir código limpio y mantenible",
  "editorial": "Prentice Hall"
}
```

### Registrar un estudiante
```
POST http://localhost:8080/api/usuarios
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "correo": "juan.perez@universidad.edu.co",
  "telefono": "3001234567",
  "tipoUsuario": "ESTUDIANTE",
  "codigoEstudiante": "EST-2024-001",
  "programa": "Ingeniería de Software"
}
```

### Registrar un ejemplar
```
POST http://localhost:8080/api/ejemplares
Content-Type: application/json

{
  "codigoEjemplar": "LIB-001-A",
  "libroId": "<id-del-libro>",
  "estado": "DISPONIBLE",
  "ubicacion": "Estante A, Fila 3"
}
```

### Registrar un préstamo
```
POST http://localhost:8080/api/prestamos
Content-Type: application/json

{
  "usuarioId": "<id-del-usuario>",
  "ejemplarId": "<id-del-ejemplar>",
  "fechaDevolucionEsperada": "2026-06-15",
  "observaciones": "Préstamo registrado por el bibliotecario"
}
```

### Registrar devolución
```
PUT http://localhost:8080/api/prestamos/<id-del-prestamo>/devolucion
```

---

## Reglas de Negocio Implementadas

1. **ISBN único**: No se pueden registrar dos libros con el mismo ISBN.
2. **Correo único**: No se pueden registrar dos usuarios con el mismo correo.
3. **Código de ejemplar único**: Cada ejemplar tiene un código físico único.
4. **Préstamo solo de disponibles**: Solo se puede prestar un ejemplar en estado `DISPONIBLE`.
5. **Cambio de estado automático**: Al prestar → `PRESTADO`; al devolver → `DISPONIBLE`.
6. **Devolución solo de activos**: Solo se puede devolver un préstamo en estado `ACTIVO`.
7. **Fecha automática**: La fecha de préstamo y devolución real se asignan automáticamente.
8. **Detección de vencidos**: Un préstamo es `vencido` si su fecha esperada ya pasó y sigue `ACTIVO`.
9. **Validación por tipo de usuario**: Estudiante requiere código y programa; Profesor requiere código y facultad; Bibliotecario requiere código de empleado y turno.

---

## Códigos de Respuesta HTTP

| Código | Significado | Cuándo ocurre |
|---|---|---|
| `200 OK` | Éxito | Consulta o actualización exitosa |
| `201 Created` | Creado | Recurso creado correctamente |
| `204 No Content` | Sin contenido | Eliminación exitosa |
| `400 Bad Request` | Datos inválidos | Validación fallida o regla de negocio violada |
| `404 Not Found` | No encontrado | El recurso solicitado no existe |
| `409 Conflict` | Conflicto de estado | Ejemplar no disponible, préstamo ya devuelto |
| `500 Internal Server Error` | Error del servidor | Error inesperado |

---

*Sistema de Gestión de Biblioteca — Diseño de Software · 4° Semestre de Ingeniería de Software*
