package co.uis.edu.agregarLibro.controller;

import co.uis.edu.agregarLibro.model.dto.LibroAddDTO;
import co.uis.edu.agregarLibro.service.LibroProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/add")
public class LibroController {

    @Autowired
    private  LibroProducerService libroProducerService;


    @PostMapping("/agregar-libro/{consumidor}")
    public void addBook(@RequestBody LibroAddDTO libroAddDTO, @PathVariable String consumidor){
        this.libroProducerService.addBook(libroAddDTO,consumidor);
    }

}
