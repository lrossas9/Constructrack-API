# Constructrack API

Constructrack API es un servicio backend simple desarrollado en Node.js que proporciona funcionalidades básicas de autenticación, incluyendo el registro y el inicio de sesión de usuarios.

## Información del Aprendiz

- **Nombre:** Laura Yineth Rosas
- **Ficha:** 3070308
- **Repositorio:** [https://github.com/lrossas9/Constructrack-API](https://github.com/lrossas9/Constructrack-API)

## Tecnologías Utilizadas

- **Node.js:** Entorno de ejecución para JavaScript del lado del servidor.
- **Express.js:** Framework para construir aplicaciones web y APIs de forma rápida y minimalista.

## Cómo Empezar

Sigue estas instrucciones para configurar y ejecutar el proyecto en tu entorno local.

### Prerrequisitos

- Tener instalado [Node.js](https://nodejs.org/es/) (que incluye npm).

### Instalación

1.  **Clona el repositorio:**
    ```bash
    git clone https://github.com/lrossas9/Constructrack-API.git
    ```
2.  **Navega a la carpeta del proyecto:**
    ```bash
    cd Constructrack-API
    ```
3.  **Instala las dependencias:**
    ```bash
    npm install
    ```

### Ejecución

Para iniciar el servidor, ejecuta el siguiente comando. La API comenzará a escuchar en el puerto `3001`.

```bash
npm start
```
Verás un mensaje en la consola que dice: `Servidor corriendo en http://localhost:3001`.

## Endpoints de la API

La API cuenta con dos endpoints principales para la gestión de usuarios.

- `POST /register`: Para registrar un nuevo usuario.
- `POST /login`: Para autenticar un usuario existente.

Para una descripción detallada de cada endpoint, incluyendo los formatos de petición y las posibles respuestas, consulta el archivo [ENDPOINTS.md](ENDPOINTS.md).

## Testing de la API

Para probar la API, puedes usar herramientas como [Postman](https://www.postman.com/) o `curl`.

En el archivo [TESTING.md](TESTING.md) encontrarás una guía paso a paso sobre cómo realizar las pruebas con Postman para cada uno de los casos de uso (registro exitoso, usuario duplicado, inicio de sesión correcto, credenciales incorrectas, etc.).
