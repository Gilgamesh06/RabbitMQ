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

    @Override
    public List<LibroResponseDTO> getAllBooks() {
        List<Libro> listaLibros = this.libroRepository.findAll();
        return listaLibros.stream()
                .map(this::convertirALibroResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LibroResponseDTO getBook(String titulo) {
        Optional<Libro> libro = this.libroRepository.findByTitulo(titulo);
        return this.convertirALibroResponseDTO(libro.orElse(null));
    }

    @Override
    public LibroResponseDTO updateBook(LibroUpdateDTO libroUpdateDTO) {
        return null;
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

        // Si el libro ya existe, puedes lanzar una excepción o retornar un valor específico
        throw new LibroEncontradoException("El libro con el título " + libroAddDTO.getTitulo() + " ya existe.");
    }
    @Override
    public LibroResponseDTO removeBook(String titulo) {
        Optional<Libro> libroOPT  = this.libroRepository.findByTitulo(titulo);
        if(libroOPT.isEmpty()){
            throw new LibroNoEncontradoException("El libro con el título " + titulo + " No existe.");
        }
        else{

            this.libroRepository.deleteById(libroOPT.get().getId());
            return convertirALibroResponseDTO(libroOPT.get());
        }

    }
}
