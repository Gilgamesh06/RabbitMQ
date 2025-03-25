package co.uis.edu.bibliotecaMalaga.model.service.interfaces;

import co.uis.edu.bibliotecaMalaga.model.dto.LibroAddDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroResponseDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroUpdateDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface IcrudLibro {

    // Listar todos los libros
    public List<LibroResponseDTO> getAllBooks();

    // Buscar Libro por titulo
    public LibroResponseDTO getBook(String titulo);

    // Actualizar Producto

    public LibroResponseDTO updateBook(LibroUpdateDTO libroUpdateDTO);

    // Agregar Libro
    public LibroResponseDTO addBook(LibroAddDTO libroAddDTO);

    // Elimiar Libro
    public LibroResponseDTO removeBook(String titulo);

}
