package co.uis.edu.bibliotecaMalaga.controller;

import co.uis.edu.bibliotecaMalaga.model.dto.LibroAddDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroResponseDTO;
import co.uis.edu.bibliotecaMalaga.repository.LibroRepository;
import co.uis.edu.bibliotecaMalaga.service.impl.LibroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/biblioteca-malaga")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService){
        this.libroService = libroService;
    }

    @GetMapping("/listar-libros")
    public List<LibroResponseDTO> getAllBooks(){
        return this.libroService.getAllBooks();
    }

    @GetMapping("/buscar-libro/{titulo}")
    public LibroResponseDTO getBook(@PathVariable String titulo){
        return this.libroService.getBook(titulo);
    }


}
