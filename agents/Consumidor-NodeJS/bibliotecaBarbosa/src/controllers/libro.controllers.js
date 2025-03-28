const LibroService = require("../services/libro.service");

class LibroController {
    static async agregarLibro(libro) {
        console.log("📥 Recibiendo mensaje para agregar libro:", libro);
        await LibroService.crearLibro(libro);
    }

    static async actualizarLibro(libro) {
        console.log("📥 Recibiendo mensaje para actualizar libro:", libro);
        await LibroService.actualizarLibro(libro);
    }

    static async eliminarLibro(titulo) {
        console.log("📥 Recibiendo mensaje para eliminar libro:", titulo);
        await LibroService.eliminarLibro(titulo);
    }
}

module.exports = LibroController;
