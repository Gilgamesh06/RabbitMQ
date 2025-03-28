package co.uis.edu.bibliotecaMalaga.service.impl;

import co.uis.edu.bibliotecaMalaga.Exception.LibroEncontradoException;
import co.uis.edu.bibliotecaMalaga.Exception.LibroNoEncontradoException;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroAddDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroResponseDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroUpdateDTO;
import co.uis.edu.bibliotecaMalaga.model.entity.Libro;
import co.uis.edu.bibliotecaMalaga.repository.LibroRepository;
import co.uis.edu.bibliotecaMalaga.model.service.interfaces.IcrudLibro;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService implements IcrudLibro {

    private final LibroRepository libroRepository;


    public LibroService(LibroRepository libroRepository){
        this.libroRepository = libroRepository;
    }

    private LibroResponseDTO convertirALibroResponseDTO(Libro libro) {
        LibroResponseDTO dto = new LibroResponseDTO();
        dto.setTitulo(libro.getTitulo());
        dto.setAutor(libro.getAutor());
        dto.setDescripcion(libro.getDescripcion());
        return dto;
    }

    private Optional<Libro> searchBook(String titulo){
        Optional<Libro> libro = this.libroRepository.findByTitulo(titulo);
        return libro;
    }

    private Libro atributesConfirm(Libro libro, LibroUpdateDTO libroUpdateDTO){
        // Actualiza solo si el autor no es nulo y no está en blanco
        if (libroUpdateDTO.getAutor() != null && !libroUpdateDTO.getAutor().isBlank()) {
            libro.setAutor(libroUpdateDTO.getAutor());
        }
        // Actualiza solo si la descripción no es nula y no está en blanco
        if (libroUpdateDTO.getDescripcion() != null && !libroUpdateDTO.getDescripcion().isBlank()) {
            libro.setDescripcion(libroUpdateDTO.getDescripcion());
        }
        // Actualiza solo si la fecha de publicación no es nula
        if (libroUpdateDTO.getFechaPublicacion() != null) {
            libro.setFechaPublicacion(libroUpdateDTO.getFechaPublicacion());
        }
        return libro;
    }

    @Override
    public List<LibroResponseDTO> getAllBooks() {
        List<Libro> listaLibros = this.libroRepository.findAll();
        return listaLibros.stream()
                .map(this::convertirALibroResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LibroResponseDTO getBook(String titulo) {
        Optional<Libro> libro = searchBook(titulo);
        return this.convertirALibroResponseDTO(libro.orElse(null));
    }

    @Override
    public LibroResponseDTO updateBook(LibroUpdateDTO libroUpdateDTO) {
        Optional<Libro> libroOpt = searchBook(libroUpdateDTO.getTitulo());

        if (libroOpt.isPresent()) {
            // Obtiene el libro existente
            Libro libro = libroOpt.get();
            // Guarda los cambios en la base de datos
            this.libroRepository.save(atributesConfirm(libro,libroUpdateDTO)); // Este metodo persiste los cambios
            // Convierte el libro actualizado a DTO y lo devuelve
            return this.convertirALibroResponseDTO(libro);
        }
        throw new LibroNoEncontradoException("El libro con el título " + libroUpdateDTO.getTitulo() + " no existe.");
    }

    @Override
    public LibroResponseDTO addBook(LibroAddDTO libroAddDTO) {
        Optional<Libro> libroOpt = this.libroRepository.findByTitulo(libroAddDTO.getTitulo());

        if (libroOpt.isEmpty()) {
            // Crear un nuevo libro
            Libro libro = new Libro();
            libro.setTitulo(libroAddDTO.getTitulo());
            libro.setAutor(libroAddDTO.getAutor());
            libro.setDescripcion(libroAddDTO.getDescripcion());
            libro.setFechaPublicacion(libroAddDTO.getFechaPublicacion());
            // Guardar el libro en la base de datos
            Libro libroGuardado = this.libroRepository.save(libro);
            // Convertir y retornar el DTO
            return this.convertirALibroResponseDTO(libroGuardado);
        }
        throw new LibroEncontradoException("El libro con el título " + libroAddDTO.getTitulo() + " ya existe.");
    }

    @Override
    public LibroResponseDTO removeBook(String titulo) {
        Optional<Libro> libroOPT = searchBook(titulo);

        if (libroOPT.isEmpty()) {
            throw new LibroNoEncontradoException("El libro con el título " + titulo + " no existe.");
        } else {
            Libro libro = libroOPT.get(); // Obtiene el libro existente
            this.libroRepository.delete(libro); // Elimina el libro
            return convertirALibroResponseDTO(libro); // Devuelve el DTO del libro eliminado
        }
    }

}
