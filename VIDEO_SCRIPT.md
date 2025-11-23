# Guion para el Video de Testing de la API Constructrack

**Objetivo del video:** Demostrar el funcionamiento y la robustez de la API Constructrack probando sus endpoints (`/register` y `/login`) con la herramienta Postman.

---

### Parte 1: Introducción (0:00 - 0:30)

**(Lo que dices)**

"Hola, mi nombre es [Tu Nombre Completo] y soy aprendiz del programa de Análisis y Desarrollo de Software. En este video, voy a realizar el testing de la API 'Constructrack', como parte de la evidencia GA7-220501096-AA5-EV02."

"Esta API, desarrollada con Node.js y Express, tiene dos endpoints principales: uno para registrar nuevos usuarios y otro para iniciar sesión."

"Para las pruebas, utilizaré Postman, una herramienta estándar en la industria para probar y documentar APIs. También mostraré que el servidor de la API está corriendo localmente."

**(Lo que muestras en pantalla)**

1.  **Preséntate a la cámara** (si lo deseas).
2.  **Muestra la terminal** donde tienes corriendo el servidor. Señala el mensaje: `Servidor corriendo en http://localhost:3001`.
3.  **Abre Postman** y muéstralo en la pantalla principal.

---

### Parte 2: Probando el Endpoint de Registro (`/register`) (0:30 - 2:30)

**(Lo que dices)**

"Vamos a empezar con el endpoint de registro. Probaré tres escenarios: un registro exitoso, un intento de registrar un usuario que ya existe y una petición con datos incompletos."

**CASO 1: REGISTRO EXITOSO**

"Primero, el caso de éxito. Voy a crear una petición POST a la URL `http://localhost:3001/register`. En el cuerpo de la petición (o 'Body'), en formato JSON, enviaré un nombre de usuario y una contraseña."

**(Lo que muestras en pantalla)**

1.  En Postman, crea una nueva petición.
2.  Selecciona el método `POST`.
3.  Pega la URL: `http://localhost:3001/register`.
4.  Ve a la pestaña `Body`, selecciona `raw` y `JSON`.
5.  Escribe el siguiente JSON:
    ```json
    {
        "username": "laura_test",
        "password": "password123"
    }
    ```
6.  Haz clic en **Send**.

**(Lo que dices)**

"Como podemos ver, la API responde con un código de estado `201 Created`, y el mensaje 'Usuario registrado satisfactoriamente'. Esto confirma que el usuario fue creado correctamente."

**(Lo que muestras en pantalla)**

1.  Señala el código de estado `201 Created` en la respuesta de Postman.
2.  Señala el cuerpo de la respuesta (el mensaje JSON).

**CASO 2: USUARIO YA EXISTENTE**

"Ahora, ¿qué pasa si intento registrar al mismo usuario otra vez? Voy a enviar la misma petición sin hacer cambios."

**(Lo que muestras en pantalla)**

1.  Haz clic en **Send** de nuevo.

**(Lo que dices)**

"Perfecto. La API responde con un código `409 Conflict` y el mensaje 'El usuario ya existe'. Esto demuestra que la validación para evitar usuarios duplicados está funcionando como se esperaba."

**(Lo que muestras en pantalla)**

1.  Señala el código de estado `409 Conflict`.
2.  Señala el mensaje de error en la respuesta.

**CASO 3: DATOS INCOMPLETOS**

"Finalmente, probemos qué sucede si no enviamos los datos requeridos. Borraré el cuerpo de la petición y la enviaré de nuevo."

**(Lo que muestras en pantalla)**

1.  Borra el contenido del `Body`.
2.  Haz clic en **Send**.

**(Lo que dices)**

"La API responde con un `400 Bad Request` y el mensaje 'Usuario y contraseña son requeridos'. Esto es correcto, ya que la API está validando que la información necesaria esté presente."

**(Lo que muestras en pantalla)**

1.  Señala el código de estado `400 Bad Request`.
2.  Señala el mensaje de error.

---

### Parte 3: Probando el Endpoint de Inicio de Sesión (`/login`) (2:30 - 4:00)

**(Lo que dices)**

"Ahora que tenemos un usuario registrado, vamos a probar el endpoint de inicio de sesión. Veremos tres casos: un inicio de sesión exitoso, uno con contraseña incorrecta y uno con un usuario que no existe."

**CASO 1: LOGIN EXITOSO**

"Crearé una nueva petición POST a `http://localhost:3001/login` con las credenciales del usuario que acabamos de crear."

**(Lo que muestras en pantalla)**

1.  Crea una nueva petición `POST` a `http://localhost:3001/login`.
2.  En el `Body` (raw, JSON), escribe:
    ```json
    {
        "username": "laura_test",
        "password": "password123"
    }
    ```
3.  Haz clic en **Send**.

**(Lo que dices)**

"Recibimos un código `200 OK` y el mensaje 'Autenticación satisfactoria'. El inicio de sesión fue exitoso."

**(Lo que muestras en pantalla)**

1.  Señala el código de estado `200 OK` y el mensaje de éxito.

**CASO 2: CONTRASEÑA INCORRECTA**

"Ahora, intentaré iniciar sesión con una contraseña incorrecta."

**(Lo que muestras en pantalla)**

1.  Cambia el valor de `password` en el `Body` a `"password_incorrecto"`.
2.  Haz clic en **Send**.

**(Lo que dices)**

"La API responde con `401 Unauthorized` y un mensaje de error en la autenticación. Esto es exactamente lo que esperábamos."

**(Lo que muestras en pantalla)**

1.  Señala el código de estado `401 Unauthorized` y el mensaje de error.

**CASO 3: USUARIO INEXISTENTE**

"Por último, intentemos con un usuario que no ha sido registrado."

**(Lo que muestras en pantalla)**

1.  Cambia el valor de `username` a `"usuario_fantasma"`.
2.  Haz clic en **Send**.

**(Lo que dices)**

"Nuevamente, recibimos un `401 Unauthorized`. La API maneja correctamente tanto usuarios como contraseñas incorrectas, protegiendo el sistema."

**(Lo que muestras en pantalla)**

1.  Señala el código de estado `401 Unauthorized`.

---

### Parte 4: Conclusión (4:00 - 4:30)

**(Lo que dices)**

"Con estas pruebas, hemos verificado que la API Constructrack funciona correctamente. Los endpoints de registro e inicio de sesión manejan tanto los casos de éxito como los diferentes escenarios de error de manera robusta y predecible."

"Esto concluye el testing de la API. Muchas gracias por su atención."

**(Lo que muestras en pantalla)**

1.  Muestra una vista general de las peticiones que creaste en Postman.
2.  Vuelve a mostrar brevemente la terminal con el servidor corriendo.
3.  Finaliza la grabación.
