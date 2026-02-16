# Endpoints de la API Constructrack

Base URL: `http://localhost:3001`

Esta documentación describe los endpoints disponibles para la evidencia del proyecto. La implementación almacena datos en memoria (arrays) para demostración.

## Autenticación

### Registrar un nuevo usuario
- **Método:** `POST`
- **URL:** `/register`
- **Body (JSON):**
  ```json
  { "username": "nombre_de_usuario", "password": "tu_contraseña" }
  ```
- **Respuesta Exitosa (201):**
  ```json
  { "message": "Usuario registrado satisfactoriamente." }
  ```
- **Errores comunes:**
  - `400` si faltan campos.
  - `409` si el usuario ya existe.

### Iniciar sesión
- **Método:** `POST`
- **URL:** `/login`
- **Body (JSON):**
  ```json
  { "username": "nombre_de_usuario", "password": "tu_contraseña" }
  ```
- **Respuesta Exitosa (200):**
  ```json
  { "message": "Autenticación satisfactoria." }
  ```
- **Errores comunes:**
  - `400` si faltan campos.
  - `401` si las credenciales no coinciden.

## Proyectos (Projects)

Cada proyecto tiene la siguiente estructura (demostración en memoria):

```json
{
  "id": 1,
  "name": "Proyecto A",
  "description": "Descripción opcional",
  "owner": "username_o_id",
  "createdAt": "2025-11-23T..."
}
```

### Listar proyectos
- **Método:** `GET`
- **URL:** `/projects`
- **Respuesta (200):** Array de proyectos.

Ejemplo:
```json
[ { "id":1, "name":"Proyecto A" }, { "id":2, "name":"Proyecto B" } ]
```

### Crear proyecto
- **Método:** `POST`
- **URL:** `/projects`
- **Body (JSON):**
  ```json
  { "name": "Proyecto A", "description": "...", "owner": "usuario1" }
  ```
- **Respuesta Exitosa (201):** Proyecto creado (objeto JSON con `id`).

### Obtener proyecto
- **Método:** `GET`
- **URL:** `/projects/:id`

### Actualizar proyecto
- **Método:** `PUT`
- **URL:** `/projects/:id`
- **Body (JSON):** Campos a actualizar (`name`, `description`, `owner`).

### Eliminar proyecto
- **Método:** `DELETE`
- **URL:** `/projects/:id`
- **Respuesta Exitosa:** `204 No Content`.

## Tareas (Tasks)

Cada tarea sigue esta estructura:

```json
{
  "id": 1,
  "projectId": 1,
  "title": "Tarea 1",
  "status": "pending", // pending | in_progress | done
  "createdAt": "2025-11-23T..."
}
```

### Listar tareas
- **Método:** `GET`
- **URL:** `/tasks`
- **Query opcional:** `?projectId=1` para filtrar por proyecto
- **Respuesta (200):** Array de tareas.

### Crear tarea
- **Método:** `POST`
- **URL:** `/tasks`
- **Body (JSON):**
  ```json
  { "projectId": 1, "title": "Instalar base", "status": "pending" }
  ```
- **Respuesta Exitosa (201):** Tarea creada (objeto JSON con `id`).

### Obtener tarea
- **Método:** `GET`
- **URL:** `/tasks/:id`

### Actualizar tarea
- **Método:** `PUT`
- **URL:** `/tasks/:id`
- **Body (JSON):** Campos a actualizar (`title`, `status`).

### Eliminar tarea
- **Método:** `DELETE`
- **URL:** `/tasks/:id`
- **Respuesta Exitosa:** `204 No Content`.

## Notas sobre uso y pruebas
- Puerto por defecto: `3001`.
- La implementación actual guarda datos en memoria: al reiniciar el servidor se pierden los datos.
- Para pruebas use Postman, Insomnia o `curl`.

Ejemplo con `curl` (crear proyecto):

```bash
curl -X POST http://localhost:3001/projects \
  -H "Content-Type: application/json" \
  -d '{"name":"Proyecto Demo","description":"Demo","owner":"laura"}'
```

Ejemplo crear tarea:

```bash
curl -X POST http://localhost:3001/tasks \
  -H "Content-Type: application/json" \
  -d '{"projectId":1,"title":"Tarea inicial"}'
```

---

Archivo de referencia: `index.js` contiene la implementación mínima para estas rutas.
