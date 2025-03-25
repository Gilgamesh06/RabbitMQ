package co.uis.edu.bibliotecaMalaga.service.component;

import co.uis.edu.bibliotecaMalaga.configuration.RabbitConfig;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroAddDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroResponseDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroDeleteDTO;
import co.uis.edu.bibliotecaMalaga.service.impl.LibroService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LibroConsumer {

    private final LibroService libroService;

    public LibroConsumer(LibroService libroService) {
        this.libroService = libroService;
    }

    // Método para agregar libros (escucha en la cola de agregar)
    @RabbitListener(queues = RabbitConfig.ADD_QUEUE_NAME)
    public LibroResponseDTO recibirLibro(LibroAddDTO libroAddDTO) {
        return this.libroService.addBook(libroAddDTO);
    }

    // Método para eliminar libros (escucha en la cola de eliminar)
    @RabbitListener(queues = RabbitConfig.DELETE_QUEUE_NAME)
    public LibroResponseDTO eliminarLibro(LibroDeleteDTO libroDeleteDTO) {
        return this.libroService.removeBook(libroDeleteDTO.getTitulo());
    }
}
