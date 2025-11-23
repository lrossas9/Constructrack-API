# TESTING - Constructrack API (GUÍA MUY DETALLADA PARA USAR POSTMAN)

Esta guía describe paso a paso cómo probar la API incluso si nunca has usado Postman. También incluye comandos alternativos con `curl` y PowerShell.

Base URL: `http://localhost:3001`

Requisitos previos
- Tener Node.js y npm instalados.
- Desde la carpeta del proyecto ejecutar:

```powershell
npm install
npm start
# Deberías ver: "Servidor corriendo en http://localhost:3001"
```

Si el servidor no está corriendo, Postman no podrá conectarse. Asegúrate de que `npm start` esté activo en una terminal.

Parte A — Instrucciones paso a paso en Postman (para principiantes absolutos)

1) Instalar Postman
- Descarga e instala Postman desde https://www.postman.com/downloads/. Usa la versión para Windows.

2) Abrir Postman y localizar botones principales
- Al abrir Postman verás una barra superior y una columna a la izquierda llamada "Collections" (Colecciones).
- Importante: en la esquina superior izquierda hay un botón llamado "Import" (Importar). Lo usarás para cargar `postman_collection.json`.

3) Importar la colección (archivo incluido en el repo)
- Haz clic en el botón "Import" (arriba a la izquierda).
- Se abrirá un modal con pestañas; selecciona la pestaña "File" (Archivo) o el área donde dice "Upload Files".
- Haz clic en "Upload Files" y selecciona el archivo `postman_collection.json` que está en la carpeta del proyecto.
- Pulsa "Import" en el modal. Verás una nueva colección llamada `Constructrack-API` en la columna izquierda bajo "Collections".

4) Abrir una petición de la colección
- En la columna izquierda, expande `Constructrack-API` y verás las peticiones: "Register", "Login", "Create Project", etc.
- Haz clic en "Create Project" para abrirla.

5) Revisar y enviar la petición
- En el panel central verás la petición dividida en pestañas: Params, Authorization, Headers, Body, Pre-request Script, Tests.
- Asegúrate de lo siguiente:
    - En la parte superior, el método sea `POST` y la URL `http://localhost:3001/projects`.
    - Haz clic en la pestaña "Headers" y confirma que exista la fila: `Content-Type` → `application/json`. Si no está, añade una fila con el botón "Key" y "Value".
    - Haz clic en la pestaña "Body". Selecciona la opción `raw` (crudo). A la derecha del selector raw hay un desplegable; cámbialo a `JSON`.
    - En el editor del body escribe (o pega):

```json
{
    "name": "Proyecto Prueba",
    "description": "Demo",
    "owner": "laura"
}
```

- Pulsa el botón azul "Send" (Enviar) que está encima del editor. Postman enviará la petición al servidor.

6) Interpretar la respuesta
- Tras enviar la petición, Postman muestra la respuesta en el panel inferior:
    - El código de estado aparece arriba a la derecha del panel de respuesta (por ejemplo: `201 Created`, `200 OK`, `400 Bad Request`).
    - El cuerpo (body) de la respuesta aparece en formato JSON en la pestaña "Body" del panel de respuesta.
    - También verás el tiempo de respuesta y el tamaño.

7) Guardar la petición y la respuesta
- Si quieres guardar la petición con cambios, haz clic en el botón "Save" (junto al nombre de la petición en la pestaña superior).
- Para guardar la respuesta como evidencia: en el panel de respuesta hay tres puntos verticales (menu); haz clic y elige "Save Response" o utiliza la opción "Save" en la UI de respuesta para almacenar la respuesta en la colección.

8) Probar otras peticiones (CRUD)
- Para `GET /projects`: abre la petición "List Projects" en la colección y pulsa "Send". Deberías ver un array con los proyectos.
- Para `GET /projects/:id`: abre "Get Project" y reemplaza `:id` en la URL por `1` (por ejemplo `http://localhost:3001/projects/1`) y pulsa "Send".
    - Nota: algunas peticiones en la colección usan `:id`; simplemente edita la URL manualmente para poner el id numérico.
- Para `PUT /projects/:id`: abre "Update Project", ve a la pestaña Body -> raw -> JSON y escribe `{"name":"Nuevo Nombre"}`, luego pulsa "Send".
- Para `DELETE /projects/:id`: abre "Delete Project", cambia `:id` por `1` y pulsa "Send"; la respuesta esperada es `204 No Content`.

9) Usar la consola de Postman para ver errores
- Para abrir la consola de Postman: en el menú superior selecciona "View" → "Show Postman Console" o usa el atajo `Ctrl+Alt+C` (Windows).
- La consola muestra detalles de la petición, cabeceras enviadas y errores de conexión si el servidor no responde.

10) Ejecutar todas las pruebas de la colección (Runner)
- En la parte superior de la columna izquierda hay un botón "Runner" o dentro de la colección (tres puntos junto al nombre) -> "Run".
- Al abrir el Runner selecciona la colección `Constructrack-API` y pulsa "Run". Esto ejecuta todas las peticiones en secuencia.
- Revisa el resultado en el Runner; te mostrará cuántas peticiones pasaron o fallaron y los códigos de estado.

Parte B — Casos de prueba detallados (qué enviar y qué esperar)

Precondición: servidor corriendo en `http://localhost:3001`.

1) Registro de usuario — Caso feliz
- Petición: `POST /register`
- Body:
```json
{ "username": "laura", "password": "secret" }
```
- Esperado: código `201` y cuerpo: `{ "message": "Usuario registrado satisfactoriamente." }`.

2) Registro sin datos — Validación
- Petición: `POST /register` con body `{}` o sin `username`/`password`.
- Esperado: código `400` y mensaje `{ "message": "Usuario y contraseña son requeridos." }`.

3) Registro duplicado
- Petición: `POST /register` con el mismo `username` ya registrado.
- Esperado: código `409` y mensaje `{ "message": "El usuario ya existe." }`.

4) Login correcto
- Petición: `POST /login` con las credenciales registradas.
- Esperado: `200` y `{ "message": "Autenticación satisfactoria." }`.

5) Login incorrecto
- Petición: `POST /login` con credenciales inválidas.
- Esperado: `401` y `{ "message": "Error en la autenticación: usuario o contraseña incorrectos." }`.

6) Crear proyecto
- Petición: `POST /projects` con body `{ "name":"Proyecto Demo", "description":"Demo","owner":"laura" }`.
- Esperado: `201` y respuesta con el objeto del proyecto (incluye `id` y `createdAt`).

7) Listar proyectos
- Petición: `GET /projects`.
- Esperado: `200` y un array que contenga el proyecto creado.

8) Obtener proyecto inexistente
- Petición: `GET /projects/9999`.
- Esperado: `404` y `{ "message": "Proyecto no encontrado." }`.

9) Actualizar proyecto
- Petición: `PUT /projects/1` con body `{ "name": "Nuevo Nombre" }`.
- Esperado: `200` y el objeto de proyecto actualizado.

10) Eliminar proyecto
- Petición: `DELETE /projects/1`.
- Esperado: `204 No Content`.

11) Crear tarea (con proyecto existente)
- Petición: `POST /tasks` con body `{ "projectId":1, "title":"Tarea inicial" }`.
- Esperado: `201` y objeto tarea con `id`, `projectId`, `status`, `createdAt`.

12) Listar tareas y filtrar
- Petición: `GET /tasks` y `GET /tasks?projectId=1`.
- Esperado: `200` y array de tareas.

13) Actualizar y eliminar tarea
- `PUT /tasks/1` con `{ "status":"in_progress" }` → `200` y tarea actualizada.
- `DELETE /tasks/1` → `204`.

Parte C — Alternativas: usar PowerShell (sin Postman)

Ejemplos (PowerShell):

```powershell
# Registrar
Invoke-RestMethod -Method POST -Uri http://localhost:3001/register -ContentType 'application/json' -Body '{"username":"laura","password":"secret"}'

# Crear proyecto
Invoke-RestMethod -Method POST -Uri http://localhost:3001/projects -ContentType 'application/json' -Body '{"name":"Proyecto Demo","description":"Demo","owner":"laura"}'

# Crear tarea
Invoke-RestMethod -Method POST -Uri http://localhost:3001/tasks -ContentType 'application/json' -Body '{"projectId":1,"title":"Tarea inicial"}'

# Listar proyectos
Invoke-RestMethod -Method GET -Uri http://localhost:3001/projects

# Listar tareas
Invoke-RestMethod -Method GET -Uri http://localhost:3001/tasks
```

Parte D — Errores comunes y soluciones

- Error: "Could not get any response" en Postman → el servidor no está corriendo. Ejecuta `npm start` y comprueba el puerto 3001.
- Error 400 → revisa que el `Content-Type` sea `application/json` y que el body sea JSON válido.
- Error 404 para recursos → verifica que el `id` existe (usar `GET /projects` para ver ids disponibles).

Parte E — Evidencia para la entrega

- Recomendación: guarda capturas de pantalla de Postman mostrando la petición, la respuesta y el código de estado.
- En Postman: después de enviar, usa el menú de la respuesta (tres puntos) → "Save Response" para adjuntar respuestas a la colección.

Si quieres, puedo generar también un pequeño script PowerShell que ejecute automáticamente los pasos de prueba y guarde las respuestas en archivos JSON.

---

Si deseas, ahora genero la colección Postman en formato JSON y la añado al repositorio.
# Guía de Pruebas de API para Constructrack con Postman

Esta guía te mostrará cómo probar los endpoints de la API de Constructrack (`/register` y `/login`) usando Postman.

## Prerrequisitos

1.  **API en ejecución:** Asegúrate de que tu servidor esté iniciado con el comando `npm start` en tu terminal. Deberías ver el mensaje: `Servidor corriendo en http://localhost:3001`.
2.  **Postman Instalado:** Si no lo tienes, descárgalo desde [postman.com/downloads](https://www.postman.com/downloads/).

## Pasos para Probar la API

Al abrir Postman, es posible que veas una pantalla de bienvenida. Puedes cerrarla o hacer clic en "Create a request" si aparece esa opción. Los siguientes pasos describen cómo probar cada endpoint desde el espacio de trabajo principal.

### 1. Probar el Endpoint de Registro (`/register`)

Este endpoint permite crear un nuevo usuario.

#### Caso de Prueba 1: Registro Exitoso

1.  **Crear una nueva solicitud:** En la ventana principal de Postman, busca y haz clic en el **botón `+` (New)**. Este botón se encuentra usualmente en la parte superior izquierda, al lado de la pestaña "Overview". Al hacerlo, se abrirá una nueva pestaña de solicitud ("Untitled Request").
2.  **Configurar la solicitud:**
    *   **Método:** A la izquierda del campo de la URL, verás un menú desplegable que por defecto dice `GET`. Haz clic en él y selecciona `POST`.
    *   **URL:** En el campo que dice **"Enter request URL"**, escribe: `http://localhost:3001/register`
3.  **Configurar el cuerpo (Body) de la solicitud:**
    *   Debajo de la barra de la URL, haz clic en la pestaña **`Body`**.
    *   Dentro de las opciones de `Body`, selecciona el botón de radio **`raw`**.
    *   A la derecha de las opciones de `raw`, verás un menú desplegable que dice `Text`. Haz clic en él y cámbialo a **`JSON`**.
4.  **Ingresar los datos:** En el área de texto que aparece debajo, introduce los datos del usuario que deseas registrar. Por ejemplo:
    ```json
    {
        "username": "laura_test",
        "password": "password123"
    }
    ```
5.  **Enviar la solicitud:** Haz clic en el botón azul **`Send`**, ubicado a la derecha de la barra de la URL.

**Resultado Esperado:**

*   **Status Code:** En la sección de respuesta (la parte inferior de la ventana), verás `201 Created`.
*   **Response Body (Cuerpo de la respuesta):**
    ```json
    {
        "message": "Usuario registrado satisfactoriamente."
    }
    ```


#### Caso de Prueba 2: Intentar Registrar un Usuario Existente

1.  Sin cambiar la configuración, haz clic nuevamente en el botón **`Send`**.

**Resultado Esperado:**

*   **Status Code:** `409 Conflict`
*   **Response Body:**
    ```json
    {
        "message": "El usuario ya existe."
    }
    ```


#### Caso de Prueba 3: Solicitud sin Datos

1.  Elimina el contenido del área de texto de la pestaña **`Body`**.
2.  Haz clic en el botón **`Send`**.

**Resultado Esperado:**

*   **Status Code:** `400 Bad Request`
*   **Response Body:**
    ```json
    {
        "message": "Usuario y contraseña son requeridos."
    }
    ```


### 2. Probar el Endpoint de Login (`/login`)

Este endpoint permite autenticar a un usuario.

#### Caso de Prueba 1: Login Exitoso

1.  **Crear una nueva solicitud:** Haz clic en el **botón `+` (New)** nuevamente para abrir otra pestaña.
2.  **Configurar la solicitud:**
    *   **Método:** Selecciona `POST`.
    *   **URL:** Escribe `http://localhost:3001/login` en el campo "Enter request URL".
3.  **Configurar el cuerpo (Body):** En la pestaña **`Body`**, selecciona **`raw`** y luego **`JSON`**.
4.  **Ingresar credenciales:** Introduce las credenciales del usuario que registraste anteriormente:
    ```json
    {
        "username": "laura_test",
        "password": "password123"
    }
    ```
5.  Haz clic en el botón **`Send`**.

**Resultado Esperado:**

*   **Status Code:** `200 OK`
*   **Response Body:**
    ```json
    {
        "message": "Autenticación satisfactoria."
    }
    ```


#### Caso de Prueba 2: Contraseña Incorrecta

1.  Modifica el valor de `password` en el área de texto de **`Body`** a un valor incorrecto. Por ejemplo:
    ```json
    {
        "username": "laura_test",
        "password": "incorrect_password"
    }
    ```
2.  Haz clic en el botón **`Send`**.

**Resultado Esperado:**

*   **Status Code:** `401 Unauthorized`
*   **Response Body:**
    ```json
    {
        "message": "Error en la autenticación: usuario o contraseña incorrectos."
    }
    ```


#### Caso de Prueba 3: Usuario Inexistente

1.  Modifica el valor de `username` en el área de texto de **`Body`** a uno que no exista. Por ejemplo:
    ```json
    {
        "username": "non_existent_user",
        "password": "password123"
    }
    ```
2.  Haz clic en el botón **`Send`**.

**Resultado Esperado:**

*   **Status Code:** `401 Unauthorized`
*   **Response Body:**
    ```json
    {
        "message": "Error en la autenticación: usuario o contraseña incorrectos."
    }
    ```

