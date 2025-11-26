# Constructrack API

Este proyecto es una API para la gestión de Constructrack.

## Información del Aprendiz

- **Nombre:** Laura Yineth Rosas
- **Ficha:** 3070308

## Instrucciones de Entrega

### Productos para entregar

Carpeta comprimida que debe tener los siguientes archivos:
1.  Archivos del proyecto.
2.  Archivo con el enlace del repositorio.

La carpeta comprimida debe tener el nombre del aprendiz y número de la evidencia así: `NOMBRE_APELLIDO_AA5_EV01`. Para este caso: `LAURA_ROSAS_AA5_EV01`.

### Extensión

- ZIP, RAR.

### Envío de la Evidencia

Para hacer el envío de la evidencia remítase al área de la actividad correspondiente y acceda al espacio: **diseño y desarrollo de servicios web - caso. GA7-220501096-AA5-EV01**.

## Enlace al Repositorio de GitHub

- **URL del Repositorio:** [https://github.com/lrossas9/Constructrack-API](https://github.com/lrossas9/Constructrack-API)

## Cómo ejecutar el proyecto (Paso a Paso)

Sigue estas instrucciones detalladas para poner en funcionamiento la API en tu computador.

### 1. Abrir una Terminal (PowerShell)

La forma más sencilla de interactuar con el código es a través de una terminal de comandos. En Windows, usaremos PowerShell.

- Presiona la tecla de **Windows** + **R**.
- En la ventana que aparece, escribe `powershell` y presiona **Enter**. Se abrirá una ventana de fondo azul o negro.

### 2. Navegar a la Carpeta del Proyecto

Debes decirle a la terminal en qué carpeta se encuentran los archivos del proyecto.

- Copia la ruta de la carpeta del proyecto. Por ejemplo: `c:\Users\TuUsuario\Ruta\a\Constructrack API`.
- En la terminal de PowerShell, escribe `cd` (que significa "change directory" o "cambiar directorio"), deja un espacio, y luego pega la ruta que copiaste. Debería verse así:
  ```powershell
  cd "c:\Users\57311\Documents\LAURA 2025 1\ANALISIS Y DESARROLLO DE SOFTWARE. (3070308)\Constructrack API"
  ```
- Presiona **Enter**. Ahora la terminal está "dentro" de la carpeta del proyecto.

### 3. Instalar las Dependencias

El proyecto necesita algunas herramientas de software para funcionar. Estas se conocen como "dependencias".

- En la misma terminal, escribe el siguiente comando y presiona **Enter**:
  ```bash
  npm install
  ```
- Espera a que el proceso termine. Puede tomar unos minutos.

### 4. Iniciar el Servidor de la API

Una vez instaladas las dependencias, ya puedes iniciar la API.

- En la misma terminal, escribe el siguiente comando y presiona **Enter**:
  ```bash
  npm start
  ```
- Verás un mensaje que dice `Servidor corriendo en http://localhost:3001`. Esto significa que la API ya está funcionando en tu computador. **No cierres esta terminal**, ya que si la cierras, la API dejará de funcionar.

## Cómo probar la API

Para verificar que la API funciona correctamente, abre **una nueva terminal de PowerShell** (repitiendo el paso 1) y usa los siguientes comandos.

### Registrar un Nuevo Usuario

Este comando envía una solicitud para crear un nuevo usuario.

- **Comando para PowerShell:**
  ```powershell
  curl -Uri http://localhost:3001/register -Method POST -Headers @{"Content-Type"="application/json"} -Body '{"username": "laura", "password": "contraseña123"}'
  ```
- **Respuesta esperada:** Deberías recibir un mensaje que indica que el usuario se registró satisfactoriamente.

### Iniciar Sesión

Este comando envía una solicitud para autenticar al usuario que acabas de crear.

- **Comando para PowerShell:**
  ```powershell
  curl -Uri http://localhost:3001/login -Method POST -Headers @{"Content-Type"="application/json"} -Body '{"username": "laura", "password": "contraseña123"}'
  ```
- **Respuesta esperada:** Deberías recibir un mensaje de "Autenticación satisfactoria".
