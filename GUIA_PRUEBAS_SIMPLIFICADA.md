# 🧪 GUÍA PRÁCTICA DE PRUEBAS (Versión Simplificada)

**Este documento te enseña a PROBAR cada característica de forma simple.**

---

## 📌 Antes de Empezar

1. **Abre Swagger**: http://localhost:8080/swagger-ui.html
2. **La API debe estar corriendo** (ver `INSTRUCCIONES_EJECUCION_PASO_A_PASO.md`)
3. **Ten lista tu contraseña**: La que registres aquí la usarás para todo

---

## 🔑 PRUEBA 1: Registrar un Usuario (Crear Cuenta)

### ¿Qué hace?
Crea un nuevo usuario que pueda usar la API.

### Pasos:

1. En Swagger, busca: **POST /api/usuarios/registro** (color verde)

2. Haz click en él para desplegarlo

3. Haz click en el botón azul: **"Try it out"**

4. Borra el contenido de **"Request body"** y pega esto:

```json
{
  "nombreUsuario": "testuser",
  "correo": "test@example.com",
  "contrasena": "Password123",
  "nombre": "Test",
  "apellido": "User",
  "telefono": "1234567890",
  "rol": "ADMINISTRADOR_OBRA"
}
```

5. Haz click en el botón azul: **"Execute"**

### ✅ Resultado esperado:
- Código: **201 Created**
- Verás un JSON con `idUsuario`

### ❌ Si falla:
- **400 Bad Request**: Usuario ya existe o datos inválidos
- **Solución**: Cambia "testuser" a otro nombre

---

## 🔓 PRUEBA 2: Login (Obtener Token)

### ¿Qué hace?
Obtienes un **token** que necesitas para hacer el resto de pruebas.

### Pasos:

1. En Swagger, busca: **POST /api/auth/login**

2. Haz click en **"Try it out"**

3. Remplaza el **Request body** con:

```json
{
  "nombreUsuario": "testuser",
  "contrasena": "Password123"
}
```

4. Haz click en **"Execute"**

### ✅ Resultado esperado:
- Código: **200 OK**
- Verás un objeto con `"token": "eyJhbGciOiJIUzI1NiIs..."`
- **Copia este token completo** (lo necesitarás para todo lo siguiente)

### ❌ Si falla:
- **400 Bad Request**: Usuario o contraseña incorrectos

---

## 📝 PRUEBA 3: Crear un Proyecto (Lo importante)

### ¿Qué hace?
Crea un proyecto de construcción nuevo.

### ⚠️ IMPORTANTE: Primero autorízate

1. En Swagger, busca el botón rojo **"Authorize"** (arriba a la derecha)

2. Haz click en él

3. En el campo **"value:"** pega tu token (sin comillas):
   ```
   eyJhbGciOiJIUzI1NiIs...
   ```

4. Haz click en **"Authorize"** y luego **"Close"**

### Ahora crea el proyecto:

1. En Swagger, busca: **POST /api/proyectos** (color verde)

2. Haz click en **"Try it out"**

3. Remplaza el **Request body** con:

```json
{
  "nombre": "Centro Comercial Nueva Era",
  "descripcion": "Construcción de centro comercial de lujo",
  "ubicacion": "Bogotá, Colombia",
  "fechaInicio": "2025-01-15",
  "fechaFin": "2025-12-31",
  "estado": "PLANIFICACIÓN",
  "presupuesto": 500000.00,
  "cliente": "Cliente ABC Corp",
  "contratista": "Constructora XYZ"
}
```

4. Haz click en **"Execute"**

### ✅ Resultado esperado:
- Código: **201 Created**
- Recibirás un `"idProyecto": 1` (o el número que sea)
- **Guarda este ID**, lo usarás después

### ❌ Si falla:
- **401 Unauthorized**: No copiaste bien el token
- **400 Bad Request**: Algún dato es inválido

---

## 📊 PRUEBA 4: Registrar Seguimiento (Avance del Proyecto)

### ¿Qué hace?
Registra que el proyecto avanzó un cierto porcentaje.

### Pasos:

1. En Swagger, busca: **POST /api/seguimiento** (color verde)

2. Haz click en **"Try it out"**

3. Remplaza el **Request body** con (cambia `idProyecto` si es diferente):

```json
{
  "idProyecto": 1,
  "avancePorcentaje": "25.50",
  "fechaSeguimiento": "2025-01-20",
  "observaciones": "Se completó excavación de cimientos",
  "estado": "EN_TIEMPO"
}
```

4. Haz click en **"Execute"**

### ✅ Resultado esperado:
- Código: **201 Created**
- Recibirás `"idSeguimiento": 1`

### 📌 Nota importante:
El porcentaje (`25.50`) **debe ser entre 0 y 100**

---

## 📈 PRUEBA 5: Consultar Avance del Proyecto (Lo que verán los jefes)

### ¿Qué hace?
Ve el porcentaje de avance actualizado del proyecto.

### Pasos:

1. En Swagger, busca: **GET /api/proyectos/{id}/avance** (color azul)

2. Haz click en **"Try it out"**

3. En el campo **id** escribe: `1` (el ID del proyecto que creaste)

4. Haz click en **"Execute"**

### ✅ Resultado esperado:
- Código: **200 OK**
- Verás:
  ```json
  {
    "idProyecto": 1,
    "nombreProyecto": "Centro Comercial Nueva Era",
    "porcentajeAvance": "25.50",
    "ultimaActualizacion": "2025-01-20"
  }
  ```

---

## 📸 PRUEBA 6: Crear Actividad (Tareas dentro del Proyecto)

### ¿Qué hace?
Crea una tarea/actividad dentro del proyecto.

### Pasos:

1. En Swagger, busca: **POST /api/proyectos/{id}/actividades** (color verde)

2. Haz click en **"Try it out"**

3. En **id** escribe: `1`

4. En **Request body**, pega:

```json
{
  "nombre": "Excavación de cimientos",
  "descripcion": "Excavación y preparación del terreno para cimientos",
  "fechaInicio": "2025-01-15",
  "fechaFin": "2025-01-25",
  "estado": "EN_PROGRESO",
  "porcentajeAvance": 60,
  "responsable": "Ing. Juan Pérez"
}
```

5. Haz click en **"Execute"**

### ✅ Resultado esperado:
- Código: **201 Created**

---

## 📋 PRUEBA 7: Registrar Reporte Diario

### ¿Qué hace?
Registra un informe diario de lo que pasó en el proyecto.

### Pasos:

1. En Swagger, busca: **POST /api/reportes/diarios** (color verde)

2. Haz click en **"Try it out"**

3. En **Request body**, pega:

```json
{
  "fecha": "2025-01-20",
  "clima": "Soleado",
  "idProyecto": 1,
  "idActividad": 1,
  "observaciones": "Se avanzó en excavación de cimientos",
  "cantidadTrabajadores": 15,
  "horasTrabajadas": 8.0,
  "novedades": "Se removió 30% de material",
  "incidentes": "Ninguno",
  "materialesUtilizados": "Gasolina, pólvora, herramientas"
}
```

4. Haz click en **"Execute"**

### ✅ Resultado esperado:
- Código: **201 Created**
- Te devuelve un `"idReporteDiario"`

---

## 📷 PRUEBA 8: Cargar Foto/Evidencia

### ¿Qué hace?
Sube una foto como prueba de que se hizo el trabajo.

### Pasos:

1. En Swagger, busca: **POST /api/reportes/evidencias** (color verde)

2. Haz click en **"Try it out"**

3. Rellena los campos:
   - **idReporteDiario**: `1` (el ID del reporte que creaste)
   - **tipoArchivo**: Selecciona `FOTO`
   - **descripcion**: Escribe algo como: `"Foto de excavación completada"`
   - **archivo**: Haz click y selecciona una imagen de tu computadora

4. Haz click en **"Execute"**

### ✅ Resultado esperado:
- Código: **201 Created**

---

## 🔍 PRUEBA 9: Ver Todos los Proyectos

### ¿Qué hace?
Listar todos los proyectos que existen.

### Pasos:

1. En Swagger, busca: **GET /api/proyectos** (color azul)

2. Haz click en **"Try it out"**

3. Haz click en **"Execute"**

### ✅ Resultado esperado:
- Código: **200 OK**
- Recibirás un array con todos tus proyectos

---

## 👤 PRUEBA 10: Ver Tu Usuario

### ¿Qué hace?
Ver los datos de tu usuario registrado.

### Pasos:

1. En Swagger, busca: **GET /api/usuarios/{id}** (color azul)

2. Haz click en **"Try it out"**

3. En **id** escribe: `1`

4. Haz click en **"Execute"**

### ✅ Resultado esperado:
- Código: **200 OK**
- Ves tu información: nombre, email, rol, etc.

---

## 📊 TABLA RÁPIDA DE PRUEBAS

| # | Acción | Endpoint | Método | Token Necesario |
|---|--------|----------|--------|-----------------|
| 1 | Registrarse | `/api/usuarios/registro` | POST | ❌ No |
| 2 | Login | `/api/auth/login` | POST | ❌ No |
| 3 | Crear Proyecto | `/api/proyectos` | POST | ✅ Sí |
| 4 | Ver Proyectos | `/api/proyectos` | GET | ✅ Sí |
| 5 | Ver Avance | `/api/proyectos/{id}/avance` | GET | ✅ Sí |
| 6 | Crear Actividad | `/api/proyectos/{id}/actividades` | POST | ✅ Sí |
| 7 | Crear Reporte | `/api/reportes/diarios` | POST | ✅ Sí |
| 8 | Cargar Foto | `/api/reportes/evidencias` | POST | ✅ Sí |
| 9 | Ver Usuario | `/api/usuarios/{id}` | GET | ✅ Sí |

---

## 🎯 Flujo Completo (Orden recomendado)

**Para ver todo funcionando, sigue este orden:**

1. ✅ **Registrate** (Prueba 1)
2. ✅ **Inicia sesión** (Prueba 2) → Copia el token
3. ✅ **Autoriza el token** (En Swagger arriba)
4. ✅ **Crea un proyecto** (Prueba 3)
5. ✅ **Registra un seguimiento** (Prueba 4)
6. ✅ **Consulta el avance** (Prueba 5)
7. ✅ **Crea una actividad** (Prueba 6)
8. ✅ **Registra un reporte** (Prueba 7)
9. ✅ **Sube una foto** (Prueba 8)
10. ✅ **Mira todos los proyectos** (Prueba 9)

---

## ❌ Errores Comunes y Soluciones

| Error | Causa | Solución |
|-------|-------|----------|
| **401 Unauthorized** | Token no copiado correctamente | Repite Login y copia TODO el token |
| **400 Bad Request** | Datos inválidos | Verifica que el JSON esté bien formateado |
| **404 Not Found** | ID no existe | Verifica que uses IDs correctos |
| **500 Internal Error** | Error del servidor | Reinicia la API |

---

## 💡 Consejos Útiles

✅ **Siempre autorízate primero** antes de hacer pruebas  
✅ **Copia bien el token** (es muy largo)  
✅ **USA IDs reales** (no inventes números)  
✅ **Verifica el JSON** (no debe tener errores de comillas)  
✅ **Espera a que terminen las solicitudes** antes de hacer otra  

---

## 📞 ¿Algo no funciona?

1. **¿Dice "401 Unauthorized"?** → Autorízate de nuevo con el botón rojo
2. **¿Dice "400 Bad Request"?** → Copia el JSON exactamente como aparece
3. **¿API no responde?** → Verifica que esté ejecutándose en PowerShell
4. **¿No ves los cambios?** → Recarga la página (F5)

---

**Última actualización**: 26 de noviembre de 2025  
**API**: Constructrack v1.0.0 (Java 21 LTS)  
**Estado**: ✅ Totalmente funcional
