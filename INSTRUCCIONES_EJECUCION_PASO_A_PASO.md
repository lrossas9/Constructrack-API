

# Instrucciones para la Ejecución del Backend Constructrack API

---

## Requisitos Previos

- Java 21 instalado (verifica con `java -version`).
- Maven instalado (opcional, solo si vas a compilar o desarrollar).

---

## 1. Compilación del Backend

Ubícate en la carpeta del proyecto:

```
cd "C:\Users\57311\Documents\LAURA 2025 1\ANALISIS Y DESARROLLO DE SOFTWARE. (3070308)\PROYECTO\Constructrack API Constructrack T\spring-constructrack"
```

Compila el proyecto y genera el archivo JAR:

```
mvn clean package -DskipTests
```

Al finalizar, verifica que el archivo `target/constructrack-api-1.0.0.jar` se haya creado correctamente.

---

## 2. Ejecución del Backend

Ejecuta el siguiente comando en la misma carpeta:

```
java -jar target/constructrack-api-1.0.0.jar
```

Espera a que aparezca el mensaje:

```
Tomcat started on port(s): 8080 (http)
Started ConstructrackApplication
```

---

## 3. Acceso a la API y Documentación

Abre tu navegador y accede a:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Consola H2 (base de datos): [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- API base: [http://localhost:8080/](http://localhost:8080/)
- Documentación técnica: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 4. Registro y Autenticación de Usuarios

En Swagger UI:

1. Registra un usuario usando el endpoint `POST /api/usuarios/registro`.
2. Autentica el usuario con `POST /api/auth/login`.
3. Copia el token JWT recibido y utilízalo en el botón "Authorize" para acceder a los endpoints protegidos.

---

## 5. Detener la API

Para detener la API, presiona `Ctrl + C` en la terminal donde está ejecutándose.

---

## 6. Solución de Problemas Comunes

| Error | Solución |
|-------|----------|
| `java is not recognized` | Instala Java 21 desde la web oficial |
| `Puerto 8080 ya está en uso` | Cierra otros programas o cambia el puerto en `application.properties` |
| `No existe el archivo JAR` | Compila el proyecto con Maven |
| `No puedo conectar a localhost:8080` | Espera unos segundos o revisa que la API esté corriendo |

---

## 7. Modo Desarrollo

Si vas a modificar el código y quieres reinicio automático:

```
mvn spring-boot:run
```

---

## Checklist de Verificación

- [ ] Java 21 instalado
- [ ] Ubicado en la carpeta `spring-constructrack`
- [ ] Archivo JAR generado en `target/`
- [ ] Terminal abierta

---

**Documento actualizado: 16 de febrero de 2026**
