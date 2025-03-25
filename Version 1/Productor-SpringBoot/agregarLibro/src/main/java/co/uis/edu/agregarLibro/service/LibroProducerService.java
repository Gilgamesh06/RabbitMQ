package co.uis.edu.agregarLibro.service;
import co.uis.edu.agregarLibro.configuration.RabbitConfig;


import co.uis.edu.agregarLibro.exception.NotFoundException;
import co.uis.edu.agregarLibro.model.dto.LibroAddDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class LibroProducerService {

    private final RabbitTemplate rabbitTemplate;
    private static final String COMMON = "common";
    private static final String SOCORRO = "socorro";
    private static final String MALAGA = "malaga";

    public LibroProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void addBook(LibroAddDTO libroAddDTO, String destino) throws NotFoundException {
        String destinolower = destino.toLowerCase();
        if( destinolower.equals(MALAGA)){
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.MALAGA_ROUTING_KEY, libroAddDTO);
        }
        else if (destinolower.equals(SOCORRO)){
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.SOCORRO_ROUTING_KEY, libroAddDTO);

        } else if (destinolower.equals(COMMON)) {
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.COMMON_ROUTING_KEY, libroAddDTO);
        }else{
            throw new NotFoundException("destino no encontrado: "+destino);
        }

    }

}
