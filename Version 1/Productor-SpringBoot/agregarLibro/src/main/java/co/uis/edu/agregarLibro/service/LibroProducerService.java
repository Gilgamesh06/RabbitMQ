package co.uis.edu.agregarLibro.service;
import co.uis.edu.agregarLibro.configuration.RabbitConfig;


import co.uis.edu.agregarLibro.exception.NotFoundException;
import co.uis.edu.agregarLibro.model.dto.LibroAddDTO;
import co.uis.edu.agregarLibro.model.dto.LibroSendDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class LibroProducerService {

    private final RabbitTemplate rabbitTemplate;
    private static final String COMMON = "common";
    private static final String SOCORRO = "socorro";
    private static final String MALAGA = "malaga";
    private static final String BARBOSA = "barbosa";

    public LibroProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public LibroSendDTO addBook(LibroAddDTO libroAddDTO, String destino) throws NotFoundException {
        String destinolower = destino.toLowerCase();
        String sede = "";
        if( destinolower.equals(MALAGA)){
            sede = "Malaga";
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.MALAGA_ROUTING_KEY, libroAddDTO);
        }
        else if (destinolower.equals(SOCORRO)) {
            sede = "Socorro";
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.SOCORRO_ROUTING_KEY, libroAddDTO);
        }else if (destinolower.equals(BARBOSA)){
                sede = "Barbosa";
                this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.BARBOSA_ROUTING_KEY, libroAddDTO);
        } else if (destinolower.equals(COMMON)){
            sede = "Todas";
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.COMMON_ROUTING_KEY, libroAddDTO);
        }else{
            throw new NotFoundException("destino no encontrado: "+destino);
        }
        LibroSendDTO libroSendDTO = new LibroSendDTO();
        libroSendDTO.setTitulo(libroAddDTO.getTitulo());
        libroSendDTO.setSede(sede);
        return libroSendDTO;

    }

}
