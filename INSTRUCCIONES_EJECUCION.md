# INSTRUCCIONES DE EJECUCIÓN - Constructrack API Backend (Spring Boot)

## Prerequisitos
- Java 17 o superior instalado
- Maven 3.8.0 o superior instalado
- Git configurado

## Pasos para Ejecutar

### Opción 1: Desde PowerShell (Recomendado para Windows)

```powershell
# 1. Navegar al directorio del proyecto
cd "C:\Users\57311\Documents\LAURA 2025 1\ANALISIS Y DESARROLLO DE SOFTWARE. (3070308)\PROYECTO\Constructrack API\spring-constructrack"

# 2. Compilar el proyecto (primera vez)
mvn clean install

# 3. Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### Opción 2: Compilar JAR y ejecutar

```powershell
# 1. Navegar al directorio del proyecto
cd "C:\Users\57311\Documents\LAURA 2025 1\ANALISIS Y DESARROLLO DE SOFTWARE. (3070308)\PROYECTO\Constructrack API\spring-constructrack"

# 2. Compilar y generar JAR
mvn clean package

# 3. Ejecutar el JAR
java -jar target/constructrack-api-1.0.0.jar
```

## Acceso a Recursos

Después de ejecutar, acceder a:

- **API Base**: http://localhost:8080
- **Swagger/OpenAPI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **H2 Console (Desarrollo)**: http://localhost:8080/h2-console

## Credenciales para H2 Console (Desarrollo)

```
JDBC URL: jdbc:h2:mem:constructrackdb
User Name: sa
Password: (dejar vacío)
```

## Estructura de Compilación Maven

```
mvn clean       # Limpia archivos anteriores
mvn compile     # Compila el código
mvn test        # Ejecuta pruebas (si existen)
mvn package     # Genera el JAR
mvn install     # Instala en repositorio local
mvn spring-boot:run  # Ejecuta directamente
```

## Verificar Instalación

### Verificar Java
```powershell
java -version
```
Debe mostrar versión 17 o superior

### Verificar Maven
```powershell
mvn -v
```
Debe mostrar versión 3.8.0 o superior

## Solución de Problemas

### Error: "mvn is not recognized"
```powershell
# Configurar PATH de Maven manualmente
$env:MAVEN_HOME = "C:\path\to\maven"
$env:PATH += ";$env:MAVEN_HOME\bin"
```

### Error: "JAVA_HOME is not set"
```powershell
# Configurar JAVA_HOME
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
```

### Puerto 8080 ya está en uso
```powershell
# Cambiar el puerto en application.properties
# Cambiar: server.port=8080
# Por: server.port=8081
```

### Limpiar caché Maven
```powershell
mvn clean -U
```

## Comandos Útiles

### Construir sin ejecutar tests
```powershell
mvn clean install -DskipTests
```

### Ver dependencias del proyecto
```powershell
mvn dependency:tree
```

### Ejecutar con perfil específico
```powershell
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Detener la aplicación (si se ejecuta en background)
```powershell
# En la terminal donde se ejecuta:
Ctrl + C
```

## Variables de Entorno (Opcional)

```powershell
# Para configuración en tiempo de ejecución
$env:SERVER_PORT = "8080"
$env:APP_JWT_SECRET = "tu_secreto_aqui"
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/constructrack"
```

## Logs

Los logs se guardan en la carpeta `logs/` (si se configura)

Verificar logs en tiempo real:
```powershell
Get-Content logs/constructrack.log -Wait
```

## Actualizar Dependencias

```powershell
mvn clean install -U -DskipTests
```

---

**Última actualización**: 26 de noviembre de 2024
**Autor**: Laura Yineth Rosas
**Ficha**: 3070308
