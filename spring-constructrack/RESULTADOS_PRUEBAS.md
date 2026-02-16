# Resultados de Pruebas de Endpoints Constructrack API

## 1. Prueba de autenticación (login)
- **Endpoint:** POST /api/auth/login
- **Body de ejemplo:**
  ```json
  {
    "nombreUsuario": "usuario_prueba",
    "contrasena": "password_prueba"
  }
  ```
- **Respuesta esperada:**
  - Código 200 OK
  - JSON con token JWT en el campo `token` o similar.

## 2. Obtener usuario por ID
- **Endpoint:** GET /api/usuario/1
- **Header:**
  - Authorization: Bearer <token JWT obtenido>
- **Respuesta esperada:**
  - Código 200 OK si existe el usuario
  - JSON con los datos del usuario
  - Código 403 si no se envía token o es inválido

## 3. Actualizar usuario
- **Endpoint:** PUT /api/usuario/1
- **Header:**
  - Authorization: Bearer <token JWT obtenido>
- **Body de ejemplo:**
  ```json
  {
    "nombre": "Nuevo Nombre",
    "correo": "nuevo@email.com"
  }
  ```
- **Respuesta esperada:**
  - Código 200 OK si la actualización es exitosa
  - Código 404 si el usuario no existe
  - Código 400 si los datos son inválidos

## 4. Desactivar usuario
- **Endpoint:** DELETE /api/usuario/1
- **Header:**
  - Authorization: Bearer <token JWT obtenido>
- **Respuesta esperada:**
  - Código 200 OK si la desactivación es exitosa
  - Código 404 si el usuario no existe

---

## Observaciones
- Todos los endpoints protegidos requieren autenticación JWT.
- Si no se envía el token, se recibe HTTP 403 Forbidden.
- El login debe realizarse primero para obtener el token.

---

_Actualizado al 14 de febrero de 2026._
