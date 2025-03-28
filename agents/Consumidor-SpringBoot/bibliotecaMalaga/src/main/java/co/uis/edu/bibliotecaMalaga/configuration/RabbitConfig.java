package co.uis.edu.bibliotecaMalaga.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "libroExchange";

    // Para agregar libros
    public static final String ADD_COMMON_ROUTING_KEY = "addbookcommon.key";
    public static final String ADD_ROUTING_KEY = "addbookmalaga.key";
    public static final String ADD_QUEUE_NAME = "libroQueueMalagaAdd";

    // Para eliminar libros
    public static final String DELETE_COMMON_ROUTING_KEY = "deletebookcommon.key";
    public static final String DELETE_ROUTING_KEY = "deletebookmalaga.key";
    public static final String DELETE_QUEUE_NAME = "libroQueueMalagaDelete";

    // Para actualizar libros
    public static final String UPDATE_COMMON_ROUTING_KEY = "updatebookcommon.key";
    public static final String UPDATE_ROUTING_KEY = "updatebookmalaga.key";
    public static final String UPDATE_QUEUE_NAME = "libroQueueMalagaUpdate";

    // Declarar cola para agregar libros
    @Bean
    public Queue addQueue() {
        return new Queue(ADD_QUEUE_NAME, false);
    }

    // Declarar cola para actualizar libros
    @Bean
    public Queue updateQueue() {
        return new Queue(UPDATE_QUEUE_NAME, false);
    }


    // Declarar cola para eliminar libros
    @Bean
    public Queue deleteQueue() {
        return new Queue(DELETE_QUEUE_NAME, false);
    }

    // Declarar el exchange DirectExchange
    @Bean
    public DirectExchange libroExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Binding para la cola de agregar libros

    // Para la clave commun
    @Bean Binding bindingAddQueueCommon(Queue addQueue, DirectExchange libroExchange){
        return BindingBuilder.bind(addQueue).to(libroExchange).with(ADD_COMMON_ROUTING_KEY);
    }

    // Para la clave de spring
    @Bean
    public Binding bindingAddQueue(Queue addQueue, DirectExchange libroExchange) {
        return BindingBuilder.bind(addQueue).to(libroExchange).with(ADD_ROUTING_KEY);
    }

    // Binding para la cola de actualizar libros

    // Para la clave commun
    @Bean Binding bindingUpdateQueueCommon(Queue updateQueue, DirectExchange libroExchange){
        return BindingBuilder.bind(updateQueue).to(libroExchange).with(UPDATE_COMMON_ROUTING_KEY);
    }

    // Para la clave de spring
    @Bean
    public Binding bindingUpdateQueue(Queue updateQueue, DirectExchange libroExchange) {
        return BindingBuilder.bind(updateQueue).to(libroExchange).with(UPDATE_ROUTING_KEY);
    }

    // Binding para la cola de eliminar libros

    // Para la clave comun
    @Bean
    public Binding bindingDeleteQueueCommon(Queue deleteQueue, DirectExchange libroExchange){
        return BindingBuilder.bind(deleteQueue).to(libroExchange).with(DELETE_COMMON_ROUTING_KEY);
    }

    // Para la clave de Spring
    @Bean
    public Binding bindingDeleteQueue(Queue deleteQueue, DirectExchange libroExchange) {
        return BindingBuilder.bind(deleteQueue).to(libroExchange).with(DELETE_ROUTING_KEY);
    }

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;
    @Value("${spring.rabbitmq.port}")
    private int rabbitPort;
    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;
    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(rabbitUsername);
        connectionFactory.setPassword(rabbitPassword);
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages(
                "co.uis.edu.agregarLibro.model.dto",
                "co.uis.edu.bibliotecaMalaga.model.dto"
        );
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
