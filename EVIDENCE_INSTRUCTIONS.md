# Instrucciones de Evidencia - GA7-220501096-AA5-EV03

Este archivo contiene las instrucciones para preparar y entregar la evidencia solicitada por la competencia:

**Evidencia de desempeño: (RAP 17) GA7-220501096-AA5-EV03: Diseño y desarrollo de servicios web - proyecto**

Requisitos de entrega:

- La carpeta comprimida debe contener:
  - Todos los archivos del proyecto (código fuente, `package.json`, `README.md`, `ENDPOINTS.md`, `TESTING.md`, etc.).
  - Un archivo de texto con el enlace al repositorio (GitHub) donde se haya hecho versionamiento.
- Nombre de la carpeta comprimida: `NOMBRE_APELLIDO_AA5_EV03` (ej.: `LAURA_ROSAS_AA5_EV03`).
- Formato aceptado: `.zip` o `.rar`.

Pasos sugeridos para preparar la entrega:

1. Verificar que el proyecto funcione localmente:

   ```powershell
   cd "<ruta_al_proyecto>"
   npm install
   npm start
   ```

2. Crear un archivo `REPO_LINK.txt` que contenga la URL del repositorio (ej.: `https://github.com/lrossas9/Constructrack-API`).

3. Asegurarse de incluir los siguientes archivos en la carpeta:
   - `index.js`
   - `package.json`
   - `README.md` (instrucciones y datos del aprendiz)
   - `ENDPOINTS.md` (documentación de servicios)
   - `TESTING.md` (pasos para probar los endpoints)
   - `EVIDENCE_INSTRUCTIONS.md` (este archivo)
   - Carpeta `.git` **(opcional)** si se desea incluir el historial localmente; si se entrega un enlace al repositorio, no es obligatorio incluir `.git`.

4. Comprimir la carpeta con el nombre solicitado:

   - En PowerShell (ejemplo creando zip):

   ```powershell
   Compress-Archive -Path .\* -DestinationPath ..\LAURA_ROSAS_AA5_EV03.zip
   ```

5. Subir/comprobar en la plataforma de la actividad y adjuntar el archivo comprimido.

Notas adicionales:

- Si no se puede incluir la carpeta `.git`, asegúrese de que el repositorio remoto contenga todo el código y el historial necesario; incluya en `REPO_LINK.txt` la URL pública del repositorio.
- Verifique que `ENDPOINTS.md` describa claramente cada servicio (método, URL, body, respuestas de ejemplo) ya que es parte de la evidencia.

Contacto del aprendiz (información incluida en `README.md`):

- Nombre: Laura Yineth Rosas
- Ficha: 3070308
- Repositorio: https://github.com/lrossas9/Constructrack-API
