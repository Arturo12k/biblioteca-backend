# Biblioteca Backend

API REST desarrollada con Spring Boot para la gestión de libros y autores.

Este proyecto forma parte de una prueba técnica. Se priorizó una versión funcional con CRUD completo, autenticación JWT, persistencia en H2, validaciones básicas, Swagger y conexión con frontend en React.

## Tecnologías utilizadas

- Java 8
- Spring Boot 2.7.18
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- H2 Database
- Maven
- Swagger / OpenAPI

## Funcionalidades implementadas

### Autenticación

- Login con credenciales.
- Generación de JWT.
- Expiración del token configurada a 1 hora.
- Endpoints protegidos con Bearer Token.
- Usuario almacenado en tabla `usuarios`.

### Autores

- Crear autor.
- Consultar autores con paginación.
- Actualizar autor parcialmente.
- Eliminar autor.
- Eliminación en cascada de libros asociados.

### Libros

- Crear libro.
- Consultar libros con paginación.
- Consultar libro por ID.
- Actualizar libro parcialmente.
- Eliminar libro.
- Búsqueda por título o autor.

### Validaciones y normalización

- Validación de campos obligatorios.
- Validación de tamaño de cadenas.
- Normalización de título de libro y nombre de autor:
    - Conversión a mayúsculas.
    - Eliminación de acentos.
    - Conversión de caracteres especiales.
    - No permite números.
    - Elimina espacios repetidos.

### Documentación

- Swagger UI disponible para visualizar y probar los endpoints.

## Endpoints principales

### Login

```http
POST /login
```

Body:

```json
{
  "username": "admin",
  "password": "123456"
}
```

Respuesta:

```json
{
  "token": "jwt-token",
  "tipo": "Bearer",
  "expiraEn": "1 hora"
}
```

### Autores

```http
GET /autores
POST /autores
PATCH /autores/{id}
DELETE /autores/{id}
```

### Libros

```http
GET /libros
GET /libros/{id}
POST /libros
PATCH /libros/{id}
DELETE /libros/{id}
```

### Búsqueda de libros

```http
GET /libros?buscar=texto
```

La búsqueda aplica sobre título del libro y nombre del autor.

## Seguridad

Todos los endpoints están protegidos con JWT, excepto:

```http
POST /login
GET /login
/h2-console/**
/swagger-ui/**
/v3/api-docs/**
```

Para consumir los endpoints protegidos se debe enviar el token en el header:

```http
Authorization: Bearer TOKEN
```

## Base de datos

El proyecto utiliza H2 en memoria.

Consola H2:

```text
http://localhost:8080/h2-console
```

Datos de conexión:

```text
JDBC URL: jdbc:h2:mem:biblioteca
User Name: sa
Password:
```

## Usuario de prueba

```text
Usuario: admin
Contraseña: 123456
```

## Cómo ejecutar el proyecto

Clonar el repositorio:

```bash
git clone URL_DEL_REPOSITORIO
cd biblioteca
```

Ejecutar:

```bash
mvn spring-boot:run
```

La API quedará disponible en:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

## Ejemplo de creación de autor

```http
POST /autores
Authorization: Bearer TOKEN
Content-Type: application/json
```

```json
{
  "nombre": "Gabriel García Márquez",
  "fechaNacimiento": "1927-03-06"
}
```

## Ejemplo de creación de libro

```http
POST /libros
Authorization: Bearer TOKEN
Content-Type: application/json
```

```json
{
  "titulo": "Cien Años de Soledad",
  "isbn": "9780307474728",
  "autorId": "ID_DEL_AUTOR",
  "numeroPaginas": 417,
  "urlPortada": "https://covers.openlibrary.org/b/isbn/9780307474728-L.jpg"
}
```

## Alcance actual

Implementado:

- CRUD de autores.
- CRUD de libros.
- Relación libro-autor.
- Eliminación en cascada.
- H2 + JPA.
- JWT.
- Spring Security.
- Swagger.
- Validaciones básicas.
- Normalización de textos.
- Búsqueda y paginación.
- Esquema SQL incluido en `schema.sql`.

Pendiente por límite de tiempo:

- Validación de ISBN mediante SOAP.
- Obtención automática de portada mediante API REST externa con WebClient.
- Carga masiva de libros mediante CSV.
- Pruebas unitarias de controlador.
- Pruebas unitarias de servicio.

## Notas

Por el límite de tiempo de la prueba, se priorizó entregar una versión funcional, ejecutable y conectada con frontend. La arquitectura se mantuvo simple para facilitar revisión, ejecución y pruebas.
