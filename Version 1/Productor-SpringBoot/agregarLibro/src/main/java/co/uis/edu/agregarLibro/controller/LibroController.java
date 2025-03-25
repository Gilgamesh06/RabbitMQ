package co.uis.edu.agregarLibro.controller;

import co.uis.edu.agregarLibro.exception.NotFoundException;
import co.uis.edu.agregarLibro.model.dto.LibroAddDTO;
import co.uis.edu.agregarLibro.service.LibroProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/add")
public class LibroController {

    @Autowired
    private  LibroProducerService libroProducerService;


    @PostMapping("/agregar-libro/{destino}")
    public void addBook(@RequestBody LibroAddDTO libroAddDTO, @PathVariable String destino) throws NotFoundException {
        this.libroProducerService.addBook(libroAddDTO,destino);
    }

}
