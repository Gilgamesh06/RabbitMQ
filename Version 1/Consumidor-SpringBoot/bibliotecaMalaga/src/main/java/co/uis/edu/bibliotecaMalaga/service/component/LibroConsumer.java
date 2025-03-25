package co.uis.edu.bibliotecaMalaga.service.component;

import co.uis.edu.bibliotecaMalaga.configuration.RabbitConfig;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroAddDTO;
import co.uis.edu.bibliotecaMalaga.model.dto.LibroResponseDTO;
import co.uis.edu.bibliotecaMalaga.service.impl.LibroService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LibroConsumer {

    private final LibroService libroService;

    public LibroConsumer(LibroService libroService){
        this.libroService = libroService;
    }


    @RabbitListener(queues = RabbitConfig.CONSUMER_QUEUE_NAME)
    public LibroResponseDTO recibirLibro(LibroAddDTO libroAddDTO) {
        return this.libroService.addBook(libroAddDTO);
    }
}