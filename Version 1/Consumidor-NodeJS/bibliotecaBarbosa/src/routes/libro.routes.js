const express = require('express');
const router = express.Router();
const LibroService = require('../services/libro.service');

// Ruta para listar todos los libros
router.get('/libros', async (req, res) => {
  try {
    const libros = await LibroService.listarLibros();
    res.json(libros);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Ruta para buscar un libro por título
router.get('/libros/:titulo', async (req, res) => {
  try {
    const { titulo } = req.params;
    const libro = await LibroService.buscarLibroPorTitulo(titulo);
    if (!libro) {
      return res.status(404).json({ error: 'Libro no encontrado' });
    }
    res.json(libro);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
