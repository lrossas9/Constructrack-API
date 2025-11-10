// Importar Express
const express = require('express');

// Crear una instancia de Express
const app = express();

// --- Middlewares ---

// Middleware para habilitar CORS (reemplaza los setHeader manuales)
app.use((req, res, next) => {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'POST, GET, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
    if (req.method === 'OPTIONS') {
        return res.sendStatus(204);
    }
    next();
});

// Middleware para parsear bodies de requests con JSON (reemplaza el parseo manual)
app.use(express.json());

// Almacenamiento en memoria para los usuarios registrados.
const users = [];

// --- RUTAS ---

// Ruta de registro
app.post('/register', (req, res) => {
    // Con express.json(), los datos ya vienen parseados en req.body
    const { username, password } = req.body;

    if (!username || !password) {
        // Con Express, las respuestas son más limpias
        return res.status(400).json({ message: 'Usuario y contraseña son requeridos.' });
    }

    const userExists = users.find(user => user.username === username);
    if (userExists) {
        return res.status(409).json({ message: 'El usuario ya existe.' });
    }

    // En una aplicación real, la contraseña debe ser hasheada.
    users.push({ username, password });
    console.log('Usuarios registrados:', users);
    res.status(201).json({ message: 'Usuario registrado satisfactoriamente.' });
});

// Ruta de inicio de sesión
app.post('/login', (req, res) => {
    const { username, password } = req.body;

    if (!username || !password) {
        return res.status(400).json({ message: 'Usuario y contraseña son requeridos.' });
    }

    const user = users.find(u => u.username === username);

    // En una aplicación real, se compararía el hash de la contraseña.
    if (!user || user.password !== password) {
        return res.status(401).json({ message: 'Error en la autenticación: usuario o contraseña incorrectos.' });
    }

    res.status(200).json({ message: 'Autenticación satisfactoria.' });
});

// Definir el puerto
const PORT = 3001;

// Iniciar el servidor con app.listen
app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
