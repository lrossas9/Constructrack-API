# Constructrack API - Backend

API REST para la gestión de proyectos de construcción, desarrollada con Spring Boot 3.1.5 y Java 21.

---

## Descripción

Este backend implementa autenticación JWT, gestión de usuarios y proyectos, reportes, evidencias y más, siguiendo buenas prácticas de arquitectura y seguridad.

---

## Documentación y Ejecución

- **Guía completa de ejecución y solución de problemas:**
	- [INSTRUCCIONES_EJECUCION_PASO_A_PASO.md](./INSTRUCCIONES_EJECUCION_PASO_A_PASO.md)
- **Documentación técnica y pruebas:**
	- [DOCUMENTACION_BACKEND.md](./DOCUMENTACION_BACKEND.md)
	- [GUIA_PRUEBAS_SIMPLIFICADA.md](./GUIA_PRUEBAS_SIMPLIFICADA.md)
	- [ARQUITECTURA.md](./ARQUITECTURA.md)

---

## Ejecución Rápida

1. Verifica que tienes Java 21 instalado (`java -version`).
2. Ubícate en la carpeta `spring-constructrack`.
3. Compila el proyecto (si es necesario):
	 ```powershell
	 mvn clean package -DskipTests
	 ```
4. Ejecuta la API:
	 ```powershell
	 java -jar target/constructrack-api-1.0.0.jar
	 ```
5. Accede a Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Para más detalles, revisa la guía de instrucciones.

---

## Recursos principales

| Recurso         | URL                                      |
|-----------------|------------------------------------------|
| Swagger UI      | http://localhost:8080/swagger-ui.html     |
| API Base        | http://localhost:8080                     |
| H2 Console      | http://localhost:8080/h2-console          |
| API Docs (JSON) | http://localhost:8080/v3/api-docs         |

---

## Estado del Proyecto

- Versión: 1.0.0
- Java: 21 LTS
- Spring Boot: 3.1.5
- Última actualización: 16 de febrero de 2026

---

## Lineamientos y Buenas Prácticas para el Desarrollo

Antes de contribuir o modificar el sistema, asegúrate de cumplir con los siguientes puntos clave:

- Estudiar detenidamente los conceptos y características definidas en el componente formativo.
- Conocer los requerimientos del sistema y manejar los documentos de casos de uso o historias de usuario.
- Familiarizarse con el funcionamiento del IDE de desarrollo seleccionado.
- Analizar y comprender el diagrama de clases, el diagrama de paquetes y el diagrama de componentes del sistema.
- Conocer los mecanismos de seguridad requeridos por la aplicación.
- Identificar las capas de la arquitectura y ubicar correctamente los componentes en cada una.
- Conocer y aplicar la metodología de desarrollo de software definida para el proyecto.
- Revisar el mapa de navegación de la aplicación para entender el flujo general.
- Codificar cada módulo en el lenguaje seleccionado (Java 21).
- Utilizar un repositorio de control de versiones (GIT recomendado) para gestionar el código fuente.
- Determinar y documentar las librerías necesarias en cada capa de la aplicación.
- Definir y documentar los frameworks utilizados en cada capa.
- Dividir los módulos en componentes reutilizables siempre que sea posible.
- Aplicar buenas prácticas de codificación y nomenclatura clara en paquetes y clases.
- Seguir patrones de diseño acordes a la arquitectura definida por componente.
- Realizar pruebas unitarias para cada módulo desarrollado.
- Documentar y aplicar las configuraciones necesarias de servidores y bases de datos.
- Documentar los ambientes de desarrollo y pruebas utilizados.

Cumplir con estos lineamientos garantiza la calidad, mantenibilidad y escalabilidad del sistema Constructrack API.

---

# Trabajo conjunto: Backend y Frontend

## ¿Cómo se conectan?

El frontend (React) y el backend (Spring Boot) trabajan por separado, pero se comunican mediante peticiones HTTP.

- El backend expone una API REST en `http://localhost:8080/api`.
- El frontend consume esa API usando Axios (ver `src/services/api.js`).
- Ambos proyectos se ejecutan en puertos diferentes.

## Ejecución de ambos proyectos

1. **Backend (Spring Boot):**
   - Ve a la carpeta del backend.
   - Ejecuta el servidor (por ejemplo, usando Maven o tu IDE).
   - El backend debe estar disponible en `http://localhost:8080`.

2. **Frontend (React):**
   - Ve a la carpeta `constructrack-react-ui`.
   - Ejecuta `npm install` para instalar dependencias.
   - Ejecuta `npm start` para iniciar el servidor de desarrollo.
   - El frontend estará disponible en `http://localhost:3000`.

## Configuración de la API

- El archivo `src/services/api.js` define la URL base:

  ```js
  const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: { 'Content-Type': 'application/json' },
  });
  ```

- Si el backend usa otro puerto, modifica la `baseURL` en `api.js`.

## Comunicación y CORS

- El frontend realiza peticiones HTTP al backend usando Axios.
- Si tienes problemas de CORS, asegúrate de que el backend permita solicitudes desde el origen del frontend.

## Recomendaciones

- Mantén el frontend y el backend en carpetas separadas.
- Usa variables de entorno para la URL de la API si necesitas cambiarla según el entorno (desarrollo/producción).
