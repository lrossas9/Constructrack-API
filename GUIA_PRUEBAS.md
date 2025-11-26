# GUÍA DE PRUEBAS - Constructrack API

## 📋 Matriz de Endpoints Implementados

| Módulo | Método | Endpoint | Autenticación | RF/RNF | Estado |
|--------|--------|----------|----------------|--------|--------|
| **AUTENTICACIÓN** |
| Auth | POST | `/api/auth/login` | ❌ No | RFC | ✅ Implementado |
| Auth | POST | `/api/auth/registrarse` | ❌ No | RFC | ✅ Implementado |
| **USUARIOS** |
| Usuarios | GET | `/api/usuarios` | ✅ Sí | RFC | ✅ Implementado |
| Usuarios | GET | `/api/usuarios/{id}` | ✅ Sí | RFC | ✅ Implementado |
| Usuarios | PUT | `/api/usuarios/{id}` | ✅ Sí | RFC | ✅ Implementado |
| Usuarios | DELETE | `/api/usuarios/{id}` | ✅ Sí | RFC | ✅ Implementado |
| **PROYECTOS** |
| Proyectos | POST | `/api/proyectos` | ✅ Sí | **RF01** | ✅ Implementado |
| Proyectos | GET | `/api/proyectos` | ✅ Sí | RFC | ✅ Implementado |
| Proyectos | GET | `/api/proyectos/{id}` | ✅ Sí | RFC | ✅ Implementado |
| Proyectos | GET | `/api/proyectos/{id}/avance` | ✅ Sí | **RF02** | ✅ Implementado |
| Proyectos | PUT | `/api/proyectos/{id}` | ✅ Sí | RFC | ✅ Implementado |
| Proyectos | DELETE | `/api/proyectos/{id}` | ✅ Sí | RFC | ✅ Implementado |
| **ACTIVIDADES** |
| Actividades | POST | `/api/proyectos/{id}/actividades` | ✅ Sí | RFC | ✅ Implementado |
| Actividades | GET | `/api/proyectos/{id}/actividades` | ✅ Sí | RFC | ✅ Implementado |
| **SEGUIMIENTO** |
| Seguimiento | POST | `/api/seguimiento` | ✅ Sí | **RF02** | ✅ Implementado |
| Seguimiento | GET | `/api/seguimiento/proyecto/{id}` | ✅ Sí | **RF02** | ✅ Implementado |
| Seguimiento | GET | `/api/seguimiento/{id}` | ✅ Sí | RFC | ✅ Implementado |
| Seguimiento | PUT | `/api/seguimiento/{id}` | ✅ Sí | RFC | ✅ Implementado |
| Seguimiento | DELETE | `/api/seguimiento/{id}` | ✅ Sí | RFC | ✅ Implementado |
| **REPORTES** |
| Reportes | POST | `/api/reportes/diarios` | ✅ Sí | RFC | ✅ Implementado |
| Reportes | POST | `/api/reportes/evidencias` | ✅ Sí | RFC | ✅ Implementado |
| Reportes | GET | `/api/reportes/proyecto/{id}` | ✅ Sí | RFC | ✅ Implementado |
| Reportes | GET | `/api/reportes/{id}` | ✅ Sí | RFC | ✅ Implementado |
| Reportes | GET | `/api/reportes/{id}/evidencias` | ✅ Sí | RFC | ✅ Implementado |

---

## 🧪 Casos de Prueba (Test Plan)

### 1. PRUEBAS DE AUTENTICACIÓN

#### Test 1.1: Registrar Usuario Exitosamente
```
POST /api/auth/registrarse
Content-Type: application/json

{
  "nombreUsuario": "testuser123",
  "correo": "test@example.com",
  "contrasena": "SecurePassword123",
  "rol": "ADMINISTRADOR_OBRA",
  "nombre": "Test",
  "apellido": "User"
}

Esperado: 201 CREATED
Verificar: Usuario creado en base de datos, contraseña hasheada
```

#### Test 1.2: Registrar Usuario Duplicado
```
POST /api/auth/registrarse
(Mismo nombreUsuario que en Test 1.1)

Esperado: 400 BAD REQUEST
Verificar: Mensaje "El usuario ya está registrado"
```

#### Test 1.3: Login Exitoso
```
POST /api/auth/login
Content-Type: application/json

{
  "nombreUsuario": "testuser123",
  "contrasena": "SecurePassword123"
}

Esperado: 200 OK
Verificar: Token JWT en respuesta, no vacío
```

#### Test 1.4: Login con Contraseña Incorrecta
```
POST /api/auth/login
{
  "nombreUsuario": "testuser123",
  "contrasena": "WrongPassword"
}

Esperado: 400 BAD REQUEST
Verificar: Mensaje "Usuario o contraseña inválidos"
```

---

### 2. PRUEBAS DE PROYECTOS (RF01)

#### Test 2.1: Crear Proyecto Exitosamente
```
POST /api/proyectos
Authorization: Bearer {token_valido}
Content-Type: application/json

{
  "nombre": "Centro Comercial Nueva Era",
  "descripcion": "Proyecto de construcción de centro comercial",
  "ubicacion": "Bogotá, Colombia",
  "fechaInicio": "2024-11-26",
  "fechaFin": "2025-06-30",
  "estado": "PLANIFICACIÓN",
  "presupuesto": 100000.00,
  "cliente": "Cliente ABC",
  "contratista": "Constructora XYZ"
}

Esperado: 201 CREATED
Verificar: 
  - idProyecto retornado
  - Tiempo de respuesta < 3 segundos (RNF01)
  - Todos los campos guardados correctamente
```

#### Test 2.2: Crear Proyecto sin Autenticación
```
POST /api/proyectos
(Sin header Authorization)

Esperado: 401 UNAUTHORIZED
```

#### Test 2.3: Crear Proyecto con Fechas Inválidas
```
POST /api/proyectos
{
  ...
  "fechaInicio": "2025-06-30",
  "fechaFin": "2024-11-26"  (anterior a fechaInicio)
  ...
}

Esperado: 400 BAD REQUEST
Verificar: Mensaje sobre fechas inválidas
```

#### Test 2.4: Obtener Todos los Proyectos
```
GET /api/proyectos
Authorization: Bearer {token_valido}

Esperado: 200 OK
Verificar: 
  - Array de proyectos retornado
  - Incluye proyecto creado en Test 2.1
  - Respuesta optimizada (< 3 segundos)
```

#### Test 2.5: Obtener Proyecto por ID
```
GET /api/proyectos/1
Authorization: Bearer {token_valido}

Esperado: 200 OK
Verificar: Datos del proyecto retornados correctamente
```

#### Test 2.6: Obtener Proyecto No Existente
```
GET /api/proyectos/99999
Authorization: Bearer {token_valido}

Esperado: 404 NOT FOUND
Verificar: Mensaje "Proyecto no encontrado"
```

---

### 3. PRUEBAS DE AVANCE (RF02)

#### Test 3.1: Obtener Avance de Proyecto sin Seguimientos
```
GET /api/proyectos/1/avance
Authorization: Bearer {token_valido}

Esperado: 200 OK
Verificar:
  - porcentajeAvance: "0.00"
  - observaciones: "Sin seguimiento registrado aún"
  - Estructura AvanceProyectoDTO correcta
```

#### Test 3.2: Registrar Seguimiento (RF02)
```
POST /api/seguimiento
Authorization: Bearer {token_valido}
Content-Type: application/json

{
  "idProyecto": 1,
  "avancePorcentaje": "25.50",
  "fechaSeguimiento": "2024-11-26",
  "observaciones": "Se completó excavación",
  "estado": "EN_TIEMPO"
}

Esperado: 201 CREATED
Verificar:
  - avancePorcentaje guardado como DECIMAL(5,2)
  - idSeguimiento retornado
  - Fecha de creación actual
```

#### Test 3.3: Registrar Seguimiento con Porcentaje Inválido
```
POST /api/seguimiento
{
  ...
  "avancePorcentaje": "150.00"  (> 100)
  ...
}

Esperado: 400 BAD REQUEST
Verificar: Mensaje "El avance no puede exceder 100"
```

#### Test 3.4: Obtener Avance Actualizado (RF02 - Tiempo Real)
```
GET /api/proyectos/1/avance
Authorization: Bearer {token_valido}

Esperado: 200 OK
Verificar:
  - porcentajeAvance: "25.50" (del Test 3.2)
  - ultimaActualizacion: "2024-11-26"
  - Datos reflejan último seguimiento registrado
```

#### Test 3.5: Registrar Múltiples Seguimientos y Verificar Último
```
1. Registrar seguimiento con 25.50%
2. Registrar seguimiento con 45.75%
3. Registrar seguimiento con 60.00%
4. GET /api/proyectos/1/avance

Esperado: porcentajeAvance retorna "60.00" (el más reciente)
```

---

### 4. PRUEBAS DE ACTIVIDADES

#### Test 4.1: Crear Actividad en Proyecto
```
POST /api/proyectos/1/actividades
Authorization: Bearer {token_valido}
Content-Type: application/json

{
  "nombre": "Excavación de cimientos",
  "descripcion": "Excavación y preparación del terreno",
  "fechaInicio": "2024-11-26",
  "fechaFin": "2024-12-10",
  "estado": "PENDIENTE",
  "porcentajeAvance": 0,
  "responsable": "Ing. Juan Pérez"
}

Esperado: 201 CREATED
Verificar: Actividad vinculada al proyecto 1
```

#### Test 4.2: Obtener Actividades del Proyecto
```
GET /api/proyectos/1/actividades
Authorization: Bearer {token_valido}

Esperado: 200 OK
Verificar: Array incluye actividad creada en Test 4.1
```

---

### 5. PRUEBAS DE REPORTES DIARIOS

#### Test 5.1: Registrar Reporte Diario
```
POST /api/reportes/diarios
Authorization: Bearer {token_valido}
Content-Type: application/json

{
  "fecha": "2024-11-26",
  "clima": "Soleado",
  "idProyecto": 1,
  "idActividad": 1,
  "observaciones": "Avance exitoso en excavación",
  "cantidadTrabajadores": 15,
  "horasTrabajadas": 8.0,
  "novedades": "Se completó 30% de excavación",
  "incidentes": "Ninguno",
  "materialesUtilizados": "Gasolina, herramientas manuales"
}

Esperado: 201 CREATED
Verificar: 
  - idReporteDiario retornado
  - Usuario registrado desde token JWT
  - Timestamp de creación actual
```

#### Test 5.2: Cargar Evidencia (Foto)
```
POST /api/reportes/evidencias
Authorization: Bearer {token_valido}
Content-Type: multipart/form-data

idReporteDiario: 1
archivo: (seleccionar imagen.jpg)
tipoArchivo: FOTO
descripcion: Foto de excavación completada

Esperado: 201 CREATED
Verificar:
  - Archivo guardado en uploads/evidencias/
  - Nombre único generado con UUID
  - Referencia en base de datos
```

#### Test 5.3: Obtener Evidencias de Reporte
```
GET /api/reportes/1/evidencias
Authorization: Bearer {token_valido}

Esperado: 200 OK
Verificar: Array incluye evidencia cargada en Test 5.2
```

---

### 6. PRUEBAS DE SEGURIDAD

#### Test 6.1: Token Expirado
```
1. Obtener token
2. Esperar 24+ horas (o modificar app.jwt.expiration-ms a valor menor)
3. GET /api/proyectos

Esperado: 401 UNAUTHORIZED
```

#### Test 6.2: Token Inválido
```
GET /api/proyectos
Authorization: Bearer invalid.token.here

Esperado: 401 UNAUTHORIZED
```

#### Test 6.3: Contraseña Hasheada Correctamente
```
1. POST /api/auth/registrarse con contrasena: "TestPassword123"
2. Verificar en base de datos (H2)
   - Contraseña NO está en texto plano
   - Comienza con $2a$ o $2b$ (formato BCrypt)
```

---

### 7. PRUEBAS DE VALIDACIÓN

#### Test 7.1: Campos Obligatorios (Registro)
```
POST /api/auth/registrarse
{
  "nombreUsuario": "",  // Vacío
  "correo": "test@example.com",
  "contrasena": "password123",
  "rol": "ADMINISTRADOR_OBRA"
}

Esperado: 400 BAD REQUEST
Verificar: Mensaje de validación
```

#### Test 7.2: Email Inválido
```
POST /api/auth/registrarse
{
  "nombreUsuario": "user",
  "correo": "invalid-email",
  "contrasena": "password123",
  "rol": "ADMINISTRADOR_OBRA"
}

Esperado: 400 BAD REQUEST
Verificar: Mensaje "El correo debe ser válido"
```

#### Test 7.3: Contraseña muy Corta
```
POST /api/auth/registrarse
{
  ...
  "contrasena": "short"  // Menos de 8 caracteres
  ...
}

Esperado: 400 BAD REQUEST
Verificar: Mensaje "debe tener mínimo 8 caracteres"
```

---

### 8. PRUEBAS DE RENDIMIENTO (RNF01)

#### Test 8.1: Crear Proyecto - Tiempo de Respuesta
```
POST /api/proyectos
(Medir tiempo desde request hasta response)

Esperado: Tiempo < 3 segundos
```

#### Test 8.2: Obtener Lista de Proyectos - Optimización
```
1. Crear 100 proyectos
2. GET /api/proyectos
3. Medir tiempo de respuesta

Esperado: 
  - Tiempo < 3 segundos
  - Uso de LAZY loading para relaciones
```

---

### 9. PRUEBAS DE DOCUMENTACIÓN SWAGGER

#### Test 9.1: Acceder a Swagger UI
```
GET http://localhost:8080/swagger-ui.html

Esperado: 
  - Interfaz Swagger cargada
  - Todos los endpoints listados
  - Esquemas de solicitud/respuesta visibles
```

#### Test 9.2: Endpoint POST /api/proyectos Documentado
```
En Swagger:
1. Buscar POST /api/proyectos
2. Clic en "Try it out"
3. Verificar campo "Requestbody" tiene esquema

Esperado:
  - nombre: string (NOT NULL)
  - descripcion: string (NOT NULL)
  - ubicacion: string (NOT NULL)
  - fechaInicio: date
  - fechaFin: date
  - etc...
```

#### Test 9.3: Endpoint GET /api/proyectos/{id}/avance Documentado
```
En Swagger:
1. Buscar GET /api/proyectos/{id}/avance
2. Verificar esquema de respuesta

Esperado:
  - idProyecto: number
  - nombreProyecto: string
  - porcentajeAvance: decimal(5,2)
  - ultimaActualizacion: date
  - etc...
```

---

## 📊 Matriz de Cobertura de Requisitos

| Requisito | Tipo | Descripción | Endpoint | Test | Estado |
|-----------|------|-------------|----------|------|--------|
| RF01 | Funcional | Registrar nuevo proyecto | POST /api/proyectos | 2.1-2.6 | ✅ |
| RF02 | Funcional | Consultar avance en tiempo real | GET /api/proyectos/{id}/avance | 3.1-3.5 | ✅ |
| RFC Auth | Funcional | Autenticación con JWT | POST /api/auth/login | 1.1-1.4 | ✅ |
| RFC Registro | Funcional | Registro con BCrypt | POST /api/auth/registrarse | 1.1-1.2 | ✅ |
| RFC Perfil | Funcional | Actualizar perfil | PUT /api/usuarios/{id} | Manual | ✅ |
| RNF01 | No Funcional | Rendimiento < 3s | Todos | 8.1-8.2 | ✅ |
| RNF Seguridad | No Funcional | JWT + BCrypt + Spring Security | Auth endpoints | 6.1-6.3 | ✅ |
| RNF Arquitectura | No Funcional | Multicapa | Estructura | Visual | ✅ |
| RNF Documentación | No Funcional | Swagger/OpenAPI | /swagger-ui.html | 9.1-9.3 | ✅ |

---

## 🎯 Checklist de Validación Pre-Entrega

- [ ] Todos los endpoints implementados
- [ ] Autenticación JWT funcional
- [ ] BCrypt implementado en registro
- [ ] Swagger/OpenAPI accesible
- [ ] Documentación completa
- [ ] Git con commits iniciales
- [ ] Base de datos H2 funcional
- [ ] Tests de seguridad pasando
- [ ] Tests de validación pasando
- [ ] Tests de rendimiento < 3 segundos
- [ ] RF01 verificado
- [ ] RF02 verificado
- [ ] RNF01 verificado
- [ ] Archivo DOCUMENTACION_BACKEND.md
- [ ] Archivo INSTRUCCIONES_EJECUCION.md

---

**Fecha de Pruebas**: 26 de noviembre de 2024
**Entorno**: Development (H2)
**Java Version**: 17+
**Maven Version**: 3.8.0+

