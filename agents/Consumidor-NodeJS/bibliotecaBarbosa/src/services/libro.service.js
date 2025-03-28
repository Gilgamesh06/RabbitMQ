const Libro = require("../models/libro.model");

class LibroService {
    static async crearLibro(datos) {
        return await Libro.create(datos);
    }

    static async actualizarLibro(datos) {
        const libro = await Libro.findOne({ where: { titulo: datos.titulo } });
        if (!libro) return null;
        return await libro.update(datos);
    }

    static async eliminarLibro(titulo) {
        return await Libro.destroy({ where: { titulo } });
    }

    static async listarLibros() {
        return await Libro.findAll();
    }

    static async buscarLibroPorTitulo(titulo) {
        return await Libro.findOne({ where: { titulo } });
    }
}

module.exports = LibroService;
