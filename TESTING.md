# TESTING - Constructrack API

Este archivo describe casos de prueba para validar la API desarrollada como evidencia del proyecto.

Base URL: `http://localhost:3001`

Recomendaciones previas:
- Asegúrate de tener el servidor en ejecución: `npm start`.
- Usa Postman, Insomnia, o `curl`/PowerShell (`Invoke-RestMethod`).

1) Autenticación

- Registrar usuario (POST /register)

  curl (Linux/macOS):
  ```bash
  curl -X POST http://localhost:3001/register \
    -H "Content-Type: application/json" \
    -d '{"username":"laura","password":"secret"}'
  ```

  PowerShell (Invoke-RestMethod):
  ```powershell
  Invoke-RestMethod -Method POST -Uri http://localhost:3001/register -ContentType 'application/json' -Body '{"username":"laura","password":"secret"}'
  ```

- Iniciar sesión (POST /login)

  ```bash
  curl -X POST http://localhost:3001/login -H "Content-Type: application/json" -d '{"username":"laura","password":"secret"}'
  ```

2) Proyectos (Projects)

- Crear proyecto (POST /projects)
  ```bash
  curl -X POST http://localhost:3001/projects -H "Content-Type: application/json" -d '{"name":"Proyecto Prueba","description":"Demo","owner":"laura"}'
  ```

- Listar proyectos (GET /projects)
  ```bash
  curl http://localhost:3001/projects
  ```

- Obtener proyecto por id (GET /projects/:id)
  ```bash
  curl http://localhost:3001/projects/1
  ```

- Actualizar proyecto (PUT /projects/:id)
  ```bash
  curl -X PUT http://localhost:3001/projects/1 -H "Content-Type: application/json" -d '{"name":"Proyecto Actualizado"}'
  ```

- Eliminar proyecto (DELETE /projects/:id)
  ```bash
  curl -X DELETE http://localhost:3001/projects/1
  ```

3) Tareas (Tasks)

- Crear tarea (POST /tasks)
  ```bash
  curl -X POST http://localhost:3001/tasks -H "Content-Type: application/json" -d '{"projectId":1,"title":"Tarea 1"}'
  ```

- Listar tareas (GET /tasks) y filtro por proyecto
  ```bash
  curl http://localhost:3001/tasks
  curl "http://localhost:3001/tasks?projectId=1"
  ```

- Obtener, actualizar y eliminar tarea
  ```bash
  curl http://localhost:3001/tasks/1
  curl -X PUT http://localhost:3001/tasks/1 -H "Content-Type: application/json" -d '{"status":"in_progress"}'
  curl -X DELETE http://localhost:3001/tasks/1
  ```

4) Casos de prueba esperados (resúmenes)
- Registro correcto devuelve `201` y mensaje.
- Registro sin datos devuelve `400`.
- Login correcto devuelve `200`.
- Login con credenciales inválidas devuelve `401`.
- CRUD de proyectos y tareas responde con códigos adecuados (`201`, `200`, `204`, `404` cuando aplica).

5) Colección Postman (sugerencia)
- Puedes crear una colección llamada "Constructrack-API" e importar los requests anteriores.
- Si quieres, genero automáticamente un archivo JSON de colección Postman y lo añado al repo.

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

