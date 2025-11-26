# 🎯 GUÍA DE EJECUCIÓN PASO A PASO (Para Principiantes)

**⏱️ Tiempo total**: 5-10 minutos  
**📦 JAR precompilado incluido**: Sí  
**💻 SO**: Windows (PowerShell)

---

## ✅ OPCIÓN MÁS FÁCIL: JAR Pre-compilado (RECOMENDADO)

**Esta es la forma más rápida si solo quieres que la API corra.**

### PASO 1️⃣: Abre PowerShell (La terminal de Windows)

#### Opción A: Desde menú de inicio
1. Presiona la tecla `Windows` (esquina inferior izquierda del teclado)
2. Escribe: `powershell`
3. Presiona `Enter`

#### Opción B: Desde cualquier carpeta
1. Abre el explorador de archivos
2. Navega a: `C:\Users\57311\Documents\LAURA 2025 1\ANALISIS Y DESARROLLO DE SOFTWARE. (3070308)\PROYECTO\Constructrack API\spring-constructrack`
3. Haz clic derecho en la barra de direcciones
4. Selecciona "Abrir PowerShell aquí"

---

### PASO 2️⃣: Navega a la carpeta del proyecto

**Copia y pega esto en PowerShell:**

```powershell
cd "C:\Users\57311\Documents\LAURA 2025 1\ANALISIS Y DESARROLLO DE SOFTWARE. (3070308)\PROYECTO\Constructrack API\spring-constructrack"
```

**Presiona `Enter`**

✔️ Deberías ver algo como:
```
PS C:\Users\57311\Documents\LAURA 2025 1\ANALISIS Y DESARROLLO DE SOFTWARE. (3070308)\PROYECTO\Constructrack API\spring-constructrack>
```

---

### PASO 3️⃣: Ejecuta la API

**Copia y pega esto:**

```powershell
java -jar target/constructrack-api-1.0.0.jar
```

**Presiona `Enter`**

---

### PASO 4️⃣: Espera a que inicie (20-30 segundos)

Verás algo como esto (buscá esta línea importante):

```
2025-11-26 13:49:36 - Tomcat started on port(s): 8080 (http) with context path ''
2025-11-26 13:49:37 - Started ConstructrackApplication in 16.677 seconds
```

**✅ CUANDO VEAS ESTO, LA API ESTÁ LISTA**

---

### PASO 5️⃣: Abre tu navegador

Ahora en tu navegador (Chrome, Firefox, Edge, etc.) abre:

**http://localhost:8080/swagger-ui.html**

---

### 🎉 ¡LISTO! Ahora puedes:

1. **Ver todos los endpoints** → Ver lista de acciones disponibles
2. **Probar cualquier endpoint** → Hacer click en "Try it out"
3. **Enviar datos** → Rellenar formulario y ver respuesta
4. **Registrar un usuario** → POST `/api/usuarios/registro`
5. **Autenticarte** → POST `/api/auth/login`

---

## ⏹️ Cómo DETENER la API

En la terminal donde está ejecutándose:

**Presiona: `Ctrl + C`**

Verás:
```
Shutdown complete
```

---

## 🔍 Verificar que todo está instalado

**ANTES de ejecutar, verifica que tengas Java instalado:**

### Verificar Java
```powershell
java -version
```

**Debe mostrar algo como:**
```
openjdk version "21.0.1"
```

**✅ Si aparece Java 21 o superior → OK**

**❌ Si dice "java is not recognized" → Necesitas instalar Java 21**

---

## 📍 Las 4 URLs Principales

**Una vez ejecutada la API, accede a:**

| URL | Para qué | Link |
|-----|----------|------|
| **Swagger (Recomendado)** | Probar endpoints visualmente | http://localhost:8080/swagger-ui.html |
| **API Base** | Ver si está viva | http://localhost:8080 |
| **Base de datos** | Ver tablas y datos (dev) | http://localhost:8080/h2-console |
| **API Docs (JSON)** | Especificación técnica | http://localhost:8080/v3/api-docs |

---

## 🎯 Ejemplo: Registrar tu primer usuario

### EN SWAGGER:

1. Abre: http://localhost:8080/swagger-ui.html

2. Busca: **POST /api/usuarios/registro** (Verde, hacia abajo)

3. Haz click en él

4. Haz click en el botón azul: **"Try it out"**

5. En el campo **Request body**, remplaza todo con esto:

```json
{
  "nombreUsuario": "miusuario",
  "correo": "mi@correo.com",
  "contrasena": "MiPassword123",
  "nombre": "Mi",
  "apellido": "Nombre",
  "rol": "ADMINISTRADOR_OBRA"
}
```

6. Haz click en el botón azul: **"Execute"**

7. ✅ Si ves código **201** → ¡Usuario creado!

---

## 🔐 Ejemplo: Autenticarte (Login)

1. En Swagger, busca: **POST /api/auth/login**

2. Haz click en él

3. Haz click en: **"Try it out"**

4. En **Request body**, remplaza con:

```json
{
  "nombreUsuario": "miusuario",
  "contrasena": "MiPassword123"
}
```

5. Haz click en: **"Execute"**

6. ✅ Si ves código **200** → Recibirás un **TOKEN JWT**

7. Copia el valor del `token` (será una cadena larga)

---

## 📊 Ejemplo: Crear un Proyecto

1. En Swagger, busca: **POST /api/proyectos**

2. Haz click en **"Try it out"**

3. En la sección **"Authorization"** arriba (rojo), pega el token que obtuviste

4. En **Request body**, remplaza con:

```json
{
  "nombre": "Centro Comercial",
  "descripcion": "Construcción de centro comercial moderno",
  "ubicacion": "Bogotá",
  "fechaInicio": "2025-01-01",
  "fechaFin": "2025-12-31",
  "estado": "PLANIFICACIÓN",
  "presupuesto": 500000.00,
  "cliente": "Cliente ABC",
  "contratista": "Constructora XYZ"
}
```

5. Haz click en **"Execute"**

6. ✅ Si ves código **201** → ¡Proyecto creado!

---

## ❌ Si algo falla...

| Error | Solución |
|-------|----------|
| "java is not recognized" | Instala Java 21 desde: https://www.oracle.com/java/technologies/downloads/#java21 |
| "Puerto 8080 ya está en uso" | Cierra otra app que use puerto 8080, o ejecuta en otro puerto (ver sección "Cambiar puerto") |
| "Archivo no encontrado" | Verifica que estés en la carpeta correcta (con `cd`) |
| "No puedo conectar a localhost:8080" | Espera 30 segundos más a que inicie, o verifica que PowerShell muestre "Tomcat started" |

---

## 🔧 OPCIONES AVANZADAS

### Cambiar el Puerto (si 8080 está ocupado)

Si quieres que la API corra en otro puerto (ej: 8081):

```powershell
java -jar target/constructrack-api-1.0.0.jar --server.port=8081
```

Luego accede a: http://localhost:8081/swagger-ui.html

---

### Compilar si hiciste cambios en el código

**Si editas código Java y quieres compilar:**

```powershell
cd "C:\Users\57311\Documents\LAURA 2025 1\ANALISIS Y DESARROLLO DE SOFTWARE. (3070308)\PROYECTO\Constructrack API\spring-constructrack"

mvn clean package -DskipTests

java -jar target/constructrack-api-1.0.0.jar
```

**Tiempo**: 1-2 minutos

---

### Modo desarrollo (Reinicia automático)

**Si quieres que reinicie automáticamente cuando cambies código:**

```powershell
cd "C:\Users\57311\Documents\LAURA 2025 1\ANALISIS Y DESARROLLO DE SOFTWARE. (3070308)\PROYECTO\Constructrack API\spring-constructrack"

mvn spring-boot:run
```

---

## 📋 Checklist de Verificación

Antes de empezar, verifica:

- [ ] ¿Tengo Java 21? (`java -version`)
- [ ] ¿Tengo PowerShell abierto?
- [ ] ¿Estoy en la carpeta correcta? (debe terminar en `spring-constructrack`)
- [ ] ¿El JAR existe? (archivo: `target/constructrack-api-1.0.0.jar`)

---

## 📞 Ayuda Rápida

| Pregunta | Respuesta |
|----------|-----------|
| ¿Dónde pongo el token? | En la sección roja "Authorization" de Swagger |
| ¿Cuánto tiempo tarda en iniciar? | 20-30 segundos normalmente |
| ¿Puedo cerrar PowerShell? | No, mientras esté cerrado la API no corre |
| ¿Los datos se guardan? | No, se pierden al cerrar (usa H2 en memoria) |
| ¿Qué hacer si falla todo? | Cierra PowerShell, borra carpeta `target`, y vuelve a compilar |

---

## 🚀 ¡Listo para empezar!

**Ejecuta estos 3 comandos en orden:**

1. ```powershell
   cd "C:\Users\57311\Documents\LAURA 2025 1\ANALISIS Y DESARROLLO DE SOFTWARE. (3070308)\PROYECTO\Constructrack API\spring-constructrack"
   ```

2. ```powershell
   java -jar target/constructrack-api-1.0.0.jar
   ```

3. **Abre en navegador:** http://localhost:8080/swagger-ui.html

**¡Que disfrutes! 🎉**

---

**Documento actualizado**: 26 de noviembre de 2025  
**Java**: 21 LTS  
**JAR**: Precompilado (56 MB)
