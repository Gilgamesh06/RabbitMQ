package co.uis.edu.agregarLibro.service;
import co.uis.edu.agregarLibro.configuration.RabbitConfig;


import co.uis.edu.agregarLibro.model.dto.LibroAddDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class LibroProducerService {

    private final RabbitTemplate rabbitTemplate;

    public LibroProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void addBook(LibroAddDTO libroAddDTO, String consumidor) {

        if( RabbitConfig.SPRINGBOOT_ROUTING_KEY.equals(consumidor)){
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.SPRINGBOOT_ROUTING_KEY, libroAddDTO);
        }
        else if (RabbitConfig.FAST_API_ROUTING_KEY.equals(consumidor)) {
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.FAST_API_ROUTING_KEY, libroAddDTO);

        } else if (RabbitConfig.COMMON_ROUTING_KEY.equals(consumidor)) {
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.COMMON_ROUTING_KEY, libroAddDTO);
        }

    }

}
