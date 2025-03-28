package co.uis.edu.bibliotecaMalaga.service.component;

import co.uis.edu.bibliotecaMalaga.Exception.LibroEncontradoException;
import co.uis.edu.bibliotecaMalaga.Exception.LibroNoEncontradoException;
import co.uis.edu.bibliotecaMalaga.configuration.RabbitConfig;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroAddDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroResponseDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroDeleteDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroUpdateDTO;
import co.uis.edu.bibliotecaMalaga.service.impl.LibroService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
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
    public LibroResponseDTO agregarLibro(LibroAddDTO libroAddDTO) {
        try {
            return this.libroService.addBook(libroAddDTO);
        } catch (LibroEncontradoException ex) {
            // Evitar reintentos re-lanzando una excepción que no reencola el mensaje
            throw new AmqpRejectAndDontRequeueException(ex.getMessage(), ex);
        }
    }
            // Metodo para actualizar libros (escucha la cola de actualizar)
    @RabbitListener(queues = RabbitConfig.UPDATE_QUEUE_NAME)
    public LibroResponseDTO actualizarLibro(LibroUpdateDTO libroUpdateDTO){
        try{
            return this.libroService.updateBook(libroUpdateDTO);
        }catch (LibroNoEncontradoException ex){
            // Evitar reintentos re-lanzando una excepción que no reencola el mensaje
            throw new AmqpRejectAndDontRequeueException(ex.getMessage(), ex);
        }
    }

    // Método para eliminar libros (escucha en la cola de eliminar)
    @RabbitListener(queues = RabbitConfig.DELETE_QUEUE_NAME)
    public LibroResponseDTO eliminarLibro(LibroDeleteDTO libroDeleteDTO) {
        try{
            return this.libroService.removeBook(libroDeleteDTO.getTitulo());
        }catch (LibroNoEncontradoException ex){
            // Evitar reintentos re-lanzando una excepción que no reencola el mensaje
            throw new AmqpRejectAndDontRequeueException(ex.getMessage(), ex);
        }
    }
}
