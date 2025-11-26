# Constructrack API - Backend Spring Boot

## 📋 Descripción General

ConstrucTrack es una API REST backend completa para la gestión de proyectos de construcción. Implementa una arquitectura multicapa (Controlador → Servicio → Repositorio) utilizando Spring Boot 3.1.5 con Java 17, siguiendo principios de Programación Orientada a Objetos (POO) y mejores prácticas de desarrollo.

**Estado del Proyecto**: ✅ Estructura Base Completada

---

## ✨ Características Principales

### ✅ Arquitectura Multicapa
- **Controllers**: Endpoints REST documentados con Swagger/OpenAPI
- **Services**: Lógica de negocio optimizada para rendimiento
- **Repositories**: Acceso a datos con operaciones CRUD personalizadas
- **Entities**: Modelos JPA con relaciones complejas

### 🔐 Seguridad
- **Autenticación JWT**: Tokens seguros con expiración configurable
- **Encriptación BCrypt**: Contraseñas hasheadas de forma robusta
- **Spring Security**: Control de acceso basado en roles
- **Validación**: Validaciones de entrada en DTOs

### ⚡ Rendimiento (RNF01)
- Optimización de consultas con LAZY loading
- Transacciones de solo lectura cuando es posible
- Índices en campos clave
- Tiempo de respuesta < 3 segundos

### 📚 Documentación
- **Swagger/OpenAPI**: Exploración interactiva de APIs
- **JavaDoc**: Documentación en código
- **DTOs Documentados**: Esquemas JSON detallados

---

## 🏗️ Estructura de Directorios

```
spring-constructrack/
├── src/main/
│   ├── java/com/constructrack/
│   │   ├── ConstructrackApplication.java      # Clase principal
│   │   ├── config/
│   │   │   └── SwaggerConfig.java            # Configuración OpenAPI
│   │   ├── controllers/                       # REST Endpoints
│   │   │   ├── AuthController.java
│   │   │   ├── UsuarioController.java
│   │   │   ├── ProyectoController.java
│   │   │   ├── SeguimientoController.java
│   │   │   └── ReporteDiarioController.java
│   │   ├── dtos/                              # Data Transfer Objects
│   │   │   ├── LoginDTO.java
│   │   │   ├── RegistroUsuarioDTO.java
│   │   │   ├── CrearProyectoDTO.java
│   │   │   ├── RegistrarSeguimientoDTO.java
│   │   │   └── ApiResponseDTO.java
│   │   ├── entities/                          # Modelos JPA
│   │   │   ├── Usuario.java
│   │   │   ├── Proyecto.java
│   │   │   ├── Actividad.java
│   │   │   ├── Seguimiento.java
│   │   │   ├── ReporteDiario.java
│   │   │   └── Evidencia.java
│   │   ├── repositories/                      # Interfaces Repository
│   │   │   ├── UsuarioRepository.java
│   │   │   ├── ProyectoRepository.java
│   │   │   ├── ActividadRepository.java
│   │   │   ├── SeguimientoRepository.java
│   │   │   ├── ReporteDiarioRepository.java
│   │   │   └── EvidenciaRepository.java
│   │   ├── security/                          # Configuración de seguridad
│   │   │   ├── JwtTokenProvider.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   └── SecurityConfig.java
│   │   └── services/                          # Lógica de negocio
│   │       ├── UsuarioService.java
│   │       ├── ProyectoService.java
│   │       ├── ActividadService.java
│   │       ├── SeguimientoService.java
│   │       ├── ReporteDiarioService.java
│   │       └── EvidenciaService.java
│   └── resources/
│       └── application.properties             # Configuración
├── pom.xml                                    # Dependencias Maven
└── README.md                                  # Esta documentación

```

---

## 🗄️ Modelo de Datos

### Entidad: USUARIO
```java
- idUsuario (PK)
- nombreUsuario (UNIQUE, NOT NULL)
- correo (UNIQUE, EMAIL)
- contrasena (HASHED con BCrypt)
- rol (ENUM: ADMINISTRADOR_OBRA, RESIDENTE_OBRA, SUPERVISOR, TRABAJADOR)
- nombre
- apellido
- telefono
- activo (Boolean)
- fechaCreacion (TIMESTAMP)
- fechaActualizacion (TIMESTAMP)
```

### Entidad: PROYECTO
```java
- idProyecto (PK)
- nombre (NOT NULL)
- descripcion
- ubicacion (NOT NULL)
- fechaInicio (NOT NULL)
- fechaFin (NOT NULL)
- estado (PLANIFICACIÓN, EN_EJECUCIÓN, SUSPENDIDO, FINALIZADO)
- presupuesto
- cliente
- contratista
- fechaCreacion (TIMESTAMP)
- fechaActualizacion (TIMESTAMP)
```

### Entidad: SEGUIMIENTO
```java
- idSeguimiento (PK)
- avancePorcentaje (DECIMAL(5,2), 0-100) ← RF02
- fechaSeguimiento
- observaciones
- estado (EN_TIEMPO, ATRASADO, ADELANTADO)
- idProyecto (FK)
- idUsuario (FK, Opcional)
- fechaCreacion (TIMESTAMP)
```

### Entidad: REPORTE_DIARIO
```java
- idReporteDiario (PK)
- fecha
- clima (NOT NULL)
- observaciones
- cantidadTrabajadores
- horasTrabajadas
- novedades
- incidentes
- materialesUtilizados
- idUsuario (FK)
- idProyecto (FK)
- idActividad (FK, Opcional)
- fechaCreacion (TIMESTAMP)
```

### Entidad: ACTIVIDAD
```java
- idActividad (PK)
- nombre
- descripcion
- fechaInicio
- fechaFin
- estado (PENDIENTE, EN_PROGRESO, COMPLETADA)
- porcentajeAvance
- responsable
- presupuestoActividad
- idProyecto (FK)
- fechaCreacion
- fechaActualizacion
```

### Entidad: EVIDENCIA
```java
- idEvidencia (PK)
- nombreArchivo
- rutaArchivo
- tipoArchivo (FOTO, DOCUMENTO, VIDEO, OTRO)
- tamanioBytes
- descripcion
- idReporteDiario (FK)
- fechaCarga (TIMESTAMP)
```

---

## 🔌 Endpoints REST Documentados

### 🔐 Módulo de Autenticación (Sin autenticación)

#### `POST /api/auth/login`
**Autentica un usuario y retorna un token JWT**

**Request Body:**
```json
{
  "nombreUsuario": "string",
  "contrasena": "string"
}
```

**Response (200 OK):**
```json
{
  "exito": true,
  "mensaje": "Autenticación exitosa",
  "datos": {
    "idUsuario": 1,
    "nombreUsuario": "laura",
    "correo": "laura@example.com",
    "rol": "ADMINISTRADOR_OBRA",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "fechaExpiracion": "2024-11-27T15:30:00"
  },
  "codigoError": null,
  "timestamp": "2024-11-26T15:30:00"
}
```

#### `POST /api/auth/registrarse`
**Registra un nuevo usuario con seguridad robusta** ← Cumple RNF de seguridad

**Request Body (Documentado para Swagger):**
```json
{
  "nombreUsuario": "string (min: 3, max: 50)",
  "correo": "email@example.com",
  "contrasena": "string (min: 8 caracteres)",
  "rol": "ADMINISTRADOR_OBRA | RESIDENTE_OBRA | SUPERVISOR | TRABAJADOR",
  "nombre": "string",
  "apellido": "string",
  "telefono": "string"
}
```

**Response (201 CREATED):**
```json
{
  "exito": true,
  "mensaje": "Usuario registrado exitosamente. Ahora puede iniciar sesión",
  "datos": null,
  "codigoError": null,
  "timestamp": "2024-11-26T15:30:00"
}
```

---

### 👤 Módulo de Usuarios (Con autenticación)

#### `GET /api/usuarios`
Obtiene todos los usuarios

#### `GET /api/usuarios/{id}`
Obtiene un usuario por ID

#### `PUT /api/usuarios/{id}`
**Actualiza el perfil de un usuario**

**Request Body:**
```json
{
  "nombre": "string",
  "apellido": "string",
  "correo": "email@example.com",
  "telefono": "string",
  "rol": "string"
}
```

#### `DELETE /api/usuarios/{id}`
Desactiva un usuario

---

### 📦 Módulo de Proyectos (Con autenticación)

#### `POST /api/proyectos`
**Crea un nuevo proyecto** ← Implementa **RF01**
Optimizado para respuesta < 3 segundos (RNF01)

**Request Body (Documentado para Swagger):**
```json
{
  "nombre": "string (NOT NULL)",
  "descripcion": "string (NOT NULL)",
  "ubicacion": "string (NOT NULL)",
  "fechaInicio": "2024-11-26",
  "fechaFin": "2024-12-31",
  "estado": "PLANIFICACIÓN",
  "presupuesto": 50000.00,
  "cliente": "Cliente ABC",
  "contratista": "Contratista XYZ"
}
```

**Response (201 CREATED):**
```json
{
  "exito": true,
  "mensaje": "Proyecto creado exitosamente",
  "datos": {
    "idProyecto": 1,
    "nombre": "Proyecto Construcción Centro Comercial",
    "ubicacion": "Bogotá, Colombia",
    "fechaInicio": "2024-11-26",
    "fechaFin": "2024-12-31",
    "estado": "PLANIFICACIÓN",
    "presupuesto": 50000.00
  },
  "timestamp": "2024-11-26T15:30:00"
}
```

#### `GET /api/proyectos`
Obtiene todos los proyectos (optimizado)

#### `GET /api/proyectos/{id}`
Obtiene un proyecto por ID

#### `GET /api/proyectos/{id}/avance`
**Obtiene el avance actual del proyecto en tiempo real** ← Implementa **RF02**

**Response (200 OK - Documentado para Swagger):**
```json
{
  "exito": true,
  "mensaje": "Avance obtenido exitosamente",
  "datos": {
    "idProyecto": 1,
    "nombreProyecto": "Proyecto Construcción Centro Comercial",
    "porcentajeAvance": "45.50",
    "ultimaActualizacion": "2024-11-26",
    "estado": "EN_EJECUCIÓN",
    "observaciones": "Avance según cronograma",
    "ubicacion": "Bogotá, Colombia",
    "fechaInicio": "2024-11-26",
    "fechaFin": "2024-12-31"
  },
  "timestamp": "2024-11-26T15:30:00"
}
```

#### `POST /api/proyectos/{id}/actividades`
**Asocia nuevas actividades a un proyecto**

**Request Body:**
```json
{
  "nombre": "string (NOT NULL)",
  "descripcion": "string",
  "fechaInicio": "2024-11-26",
  "fechaFin": "2024-12-10",
  "estado": "PENDIENTE",
  "porcentajeAvance": 0,
  "responsable": "string",
  "presupuestoActividad": 5000.00
}
```

#### `GET /api/proyectos/{idProyecto}/actividades`
Obtiene todas las actividades de un proyecto

#### `PUT /api/proyectos/{id}`
Actualiza un proyecto

#### `DELETE /api/proyectos/{id}`
Elimina un proyecto

---

### 📊 Módulo de Seguimiento (Con autenticación)

#### `POST /api/seguimiento`
**Registra el avance de obra** ← Implementa **RF02**
Maneja avancePorcentaje como DECIMAL(5,2)

**Request Body:**
```json
{
  "idProyecto": 1,
  "avancePorcentaje": "45.50",
  "fechaSeguimiento": "2024-11-26",
  "observaciones": "Avance acumulado hasta hoy",
  "estado": "EN_TIEMPO"
}
```

#### `GET /api/seguimiento/proyecto/{idProyecto}`
Obtiene todos los seguimientos de un proyecto

#### `GET /api/seguimiento/{id}`
Obtiene un seguimiento por ID

#### `PUT /api/seguimiento/{id}`
Actualiza un seguimiento

#### `DELETE /api/seguimiento/{id}`
Elimina un seguimiento

---

### 📋 Módulo de Informes (Con autenticación)

#### `POST /api/reportes/diarios`
**Registra el informe diario de obra**

**Request Body:**
```json
{
  "fecha": "2024-11-26",
  "clima": "Soleado",
  "idProyecto": 1,
  "idActividad": 1,
  "observaciones": "Día productivo",
  "cantidadTrabajadores": 15,
  "horasTrabajadas": 8.0,
  "novedades": "Se avanzó con cimientos",
  "incidentes": "Ninguno",
  "materialesUtilizados": "Cemento, arena, grava"
}
```

#### `POST /api/reportes/evidencias`
**Carga archivos o fotos asociadas a reportes**

**Request Form-Data:**
```
idReporteDiario: 1
archivo: [MultipartFile]
tipoArchivo: FOTO | DOCUMENTO | VIDEO | OTRO
descripcion: Foto de cimientos completados
```

#### `GET /api/reportes/proyecto/{idProyecto}`
Obtiene todos los reportes de un proyecto

#### `GET /api/reportes/{id}`
Obtiene un reporte por ID

#### `GET /api/reportes/{idReporteDiario}/evidencias`
Obtiene las evidencias de un reporte

---

## 🔑 Autenticación y Autorización

### Flujo de Autenticación
1. **Registro**: POST `/api/auth/registrarse`
   - Contraseña se hashea con BCrypt
   - Usuario se crea con rol específico

2. **Login**: POST `/api/auth/login`
   - Se validan credenciales
   - Se genera token JWT con 24 horas de validez
   - Token se retorna al cliente

3. **Uso de API**: Todos los requests posteriores
   - Agregar encabezado: `Authorization: Bearer {token}`
   - El filtro JwtAuthenticationFilter valida el token
   - Si es válido, se procesa la solicitud
   - Si no es válido, retorna 401 Unauthorized

### Roles Disponibles
```
ADMINISTRADOR_OBRA      - Acceso total a todas las operaciones
RESIDENTE_OBRA         - Acceso a proyectos y reportes
SUPERVISOR             - Acceso a seguimiento y verificación
TRABAJADOR             - Acceso limitado a reportes
ADMINISTRADOR_SISTEMA  - Gestión del sistema
```

---

## 🛠️ Requisitos del Sistema

### Mínimos
- **Java**: 17 o superior
- **Maven**: 3.8.0 o superior
- **Base de Datos**: H2 (desarrollo), MySQL 8+ (producción)

### Dependencias Principales
- Spring Boot 3.1.5
- Spring Data JPA
- Spring Security
- JWT (jjwt 0.12.3)
- BCrypt
- Swagger/OpenAPI (springdoc 2.1.0)
- MySQL Connector
- PostgreSQL Driver
- H2 Database

---

## 🚀 Instalación y Ejecución

### 1. Clonar el Repositorio
```bash
git clone https://github.com/lrossas9/Constructrack-API.git
cd spring-constructrack
```

### 2. Configurar la Base de Datos

#### Opción A: H2 (Desarrollo - Recomendado para inicio rápido)
Las configuraciones ya están en `application.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:constructrackdb
spring.datasource.driverClassName=org.h2.Driver
spring.h2.console.enabled=true
```

#### Opción B: MySQL (Producción)
Descomenta en `application.properties` y configura:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/constructrack_db
spring.datasource.username=root
spring.datasource.password=tu_contraseña
```

### 3. Compilar el Proyecto
```bash
mvn clean install
```

### 4. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 📖 Acceder a la Documentación

### Swagger/OpenAPI UI
```
http://localhost:8080/swagger-ui.html
```

### Especificación OpenAPI JSON
```
http://localhost:8080/v3/api-docs
```

### H2 Console (solo desarrollo)
```
http://localhost:8080/h2-console
```

---

## 💾 Ejemplo de Uso Completo (cURL)

### 1. Registrar un Usuario
```bash
curl -X POST http://localhost:8080/api/auth/registrarse \
  -H "Content-Type: application/json" \
  -d '{
    "nombreUsuario": "laura",
    "correo": "laura@example.com",
    "contrasena": "password123",
    "rol": "ADMINISTRADOR_OBRA",
    "nombre": "Laura",
    "apellido": "Rosas"
  }'
```

### 2. Iniciar Sesión
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "nombreUsuario": "laura",
    "contrasena": "password123"
  }'
```

Guardar el `token` retornado.

### 3. Crear un Proyecto (usando el token)
```bash
curl -X POST http://localhost:8080/api/proyectos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "nombre": "Centro Comercial Nueva Era",
    "descripcion": "Construcción de centro comercial de 5 pisos",
    "ubicacion": "Bogotá, Colombia",
    "fechaInicio": "2024-11-26",
    "fechaFin": "2025-06-30",
    "estado": "PLANIFICACIÓN",
    "presupuesto": 100000.00
  }'
```

### 4. Consultar Avance (RF02)
```bash
curl -X GET http://localhost:8080/api/proyectos/1/avance \
  -H "Authorization: Bearer {token}"
```

### 5. Registrar Seguimiento (RF02)
```bash
curl -X POST http://localhost:8080/api/seguimiento \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {token}" \
  -d '{
    "idProyecto": 1,
    "avancePorcentaje": "15.50",
    "fechaSeguimiento": "2024-11-26",
    "observaciones": "Se completó excavación de cimientos"
  }'
```

---

## 📊 Requisitos Cumplidos

### ✅ Requisitos Funcionales (RF)
- **RF01**: ✓ POST `/api/proyectos` - Registro de nuevo proyecto
- **RF02**: ✓ GET `/api/proyectos/{id}/avance` - Consulta de avance en tiempo real
- **RFC Autenticación**: ✓ POST `/api/auth/login` - Token JWT
- **RFC Registro**: ✓ POST `/api/auth/registrarse` - Validación y BCrypt
- **RFC Perfil**: ✓ PUT `/api/usuarios/{id}` - Actualización de datos

### ✅ Requisitos No Funcionales (RNF)
- **RNF01 Rendimiento**: ✓ Respuesta < 3 segundos (optimizado con LAZY loading)
- **RNF Seguridad**: ✓ JWT + BCrypt + Spring Security
- **RNF Arquitectura**: ✓ Multicapa (Controller → Service → Repository)
- **RNF Documentación**: ✓ Swagger/OpenAPI completo

### ✅ Documentación
- **Swagger/OpenAPI**: ✓ Exploración interactiva
- **Esquemas JSON**: ✓ POST `/api/proyectos`, POST `/api/usuarios/registro`, GET `/api/proyectos/{id}/avance`
- **JavaDoc**: ✓ Métodos y clases documentados

---

## 🔧 Configuración Adicional

### JWT
```properties
app.jwt.secret=ConstructrackSecretKeyMustBeAtLeast256BitsLongForHS256...
app.jwt.expiration-ms=86400000  # 24 horas
```

### Multipart File Upload
```properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

---

## 📝 Notas de Desarrollo

- **POO**: Implementación completa con herencia, polimorfismo y encapsulamiento
- **DTOs**: Separación clara entre datos de entrada y modelos internos
- **Transacciones**: `@Transactional` en servicios para consistencia
- **Validación**: Bean Validation (Jakarta Validation) en DTOs
- **Logging**: SLF4J con niveles configurables
- **CORS**: Requiere configuración adicional si se usa con frontend separado

---

## 🐛 Solución de Problemas

### Error: "Cannot find symbol: class SwaggerConfig"
- Ejecutar: `mvn clean install`

### Error: "No suitable driver found for jdbc:h2"
- Asegurar que H2 está en las dependencias de Maven

### Error: "Token JWT inválido"
- Verificar que el token está completo en el encabezado Authorization
- Verificar que no ha expirado (24 horas)

---

## 📬 Contacto y Soporte

- **Desarrollador**: Laura Yineth Rosas
- **Ficha**: 3070308
- **Repositorio**: [GitHub - Constructrack-API](https://github.com/lrossas9/Constructrack-API)

---

## 📄 Licencia

Este proyecto es parte de la evaluación del programa de ANÁLISIS Y DESARROLLO DE SOFTWARE.

---

**Última actualización**: 26 de noviembre de 2024
**Versión**: 1.0.0
