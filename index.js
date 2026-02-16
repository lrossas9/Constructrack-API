// Importar Express
const express = require('express');

// Crear una instancia de Express
const app = express();

// --- Middlewares ---

// Middleware para habilitar CORS (reemplaza los setHeader manuales)
app.use((req, res, next) => {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'POST, GET, PUT, DELETE, OPTIONS');
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
// Almacenamiento en memoria para proyectos y tareas (para demostración)
const projects = [];
const tasks = [];

let projectIdCounter = 1;
let taskIdCounter = 1;

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

// --- Projects CRUD ---

// Listar todos los proyectos
app.get('/projects', (req, res) => {
    res.status(200).json(projects);
});

// Crear un nuevo proyecto
app.post('/projects', (req, res) => {
    const { name, description, owner } = req.body;
    if (!name) {
        return res.status(400).json({ message: 'El nombre del proyecto es requerido.' });
    }
    const project = { id: projectIdCounter++, name, description: description || '', owner: owner || null, createdAt: new Date().toISOString() };
    projects.push(project);
    res.status(201).json(project);
});

// Obtener un proyecto por id
app.get('/projects/:id', (req, res) => {
    const id = parseInt(req.params.id, 10);
    const project = projects.find(p => p.id === id);
    if (!project) return res.status(404).json({ message: 'Proyecto no encontrado.' });
    res.status(200).json(project);
});

// Actualizar un proyecto
app.put('/projects/:id', (req, res) => {
    const id = parseInt(req.params.id, 10);
    const project = projects.find(p => p.id === id);
    if (!project) return res.status(404).json({ message: 'Proyecto no encontrado.' });
    const { name, description, owner } = req.body;
    if (name) project.name = name;
    if (description !== undefined) project.description = description;
    if (owner !== undefined) project.owner = owner;
    res.status(200).json(project);
});

// Eliminar un proyecto
app.delete('/projects/:id', (req, res) => {
    const id = parseInt(req.params.id, 10);
    const idx = projects.findIndex(p => p.id === id);
    if (idx === -1) return res.status(404).json({ message: 'Proyecto no encontrado.' });
    projects.splice(idx, 1);
    // Opcional: eliminar tareas asociadas
    for (let i = tasks.length - 1; i >= 0; i--) {
        if (tasks[i].projectId === id) tasks.splice(i, 1);
    }
    res.status(204).send();
});

// --- Tasks CRUD ---

// Listar tareas (opcional filter por projectId)
app.get('/tasks', (req, res) => {
    const { projectId } = req.query;
    if (projectId) {
        const pid = parseInt(projectId, 10);
        return res.status(200).json(tasks.filter(t => t.projectId === pid));
    }
    res.status(200).json(tasks);
});

// Crear tarea
app.post('/tasks', (req, res) => {
    const { projectId, title, status } = req.body;
    if (!projectId || !title) return res.status(400).json({ message: 'projectId y title son requeridos.' });
    const project = projects.find(p => p.id === Number(projectId));
    if (!project) return res.status(404).json({ message: 'Proyecto asociado no encontrado.' });
    const task = { id: taskIdCounter++, projectId: Number(projectId), title, status: status || 'pending', createdAt: new Date().toISOString() };
    tasks.push(task);
    res.status(201).json(task);
});

// Obtener tarea
app.get('/tasks/:id', (req, res) => {
    const id = parseInt(req.params.id, 10);
    const task = tasks.find(t => t.id === id);
    if (!task) return res.status(404).json({ message: 'Tarea no encontrada.' });
    res.status(200).json(task);
});

// Actualizar tarea
app.put('/tasks/:id', (req, res) => {
    const id = parseInt(req.params.id, 10);
    const task = tasks.find(t => t.id === id);
    if (!task) return res.status(404).json({ message: 'Tarea no encontrada.' });
    const { title, status } = req.body;
    if (title) task.title = title;
    if (status) task.status = status;
    res.status(200).json(task);
});

// Eliminar tarea
app.delete('/tasks/:id', (req, res) => {
    const id = parseInt(req.params.id, 10);
    const idx = tasks.findIndex(t => t.id === id);
    if (idx === -1) return res.status(404).json({ message: 'Tarea no encontrada.' });
    tasks.splice(idx, 1);
    res.status(204).send();
});

// Definir el puerto
const PORT = 3001;

// Iniciar el servidor con app.listen
app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
