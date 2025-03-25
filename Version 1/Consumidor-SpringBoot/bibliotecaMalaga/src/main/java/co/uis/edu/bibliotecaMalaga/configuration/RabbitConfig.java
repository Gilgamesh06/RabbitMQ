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

    // Constantes para el consumidor
    public static final String EXCHANGE_NAME = "libroExchange";
    public static final String COMMON_ROUTING_KEY = "common.key";
    public static final String SPRING_ROUTING_KEY = "spring.key";
    public static final String CONSUMER_QUEUE_NAME = "libroQueueBiblioteca";

    // Declarar la cola del consumidor
    @Bean
    public Queue consumerQueue() {
        return new Queue(CONSUMER_QUEUE_NAME, false);
    }

    // Declarar el exchange DirectExchange
    @Bean
    public DirectExchange libroExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Vinculación para la key común
    @Bean
    public Binding bindingConsumerCommon(Queue consumerQueue, DirectExchange libroExchange) {
        return BindingBuilder.bind(consumerQueue).to(libroExchange).with(COMMON_ROUTING_KEY);
    }

    // Vinculación para la key spring
    @Bean
    public Binding bindingConsumerSpring(Queue consumerQueue, DirectExchange libroExchange) {
        return BindingBuilder.bind(consumerQueue).to(libroExchange).with(SPRING_ROUTING_KEY);
    }

    // Configuración de conexión
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

    // Configuración del convertidor de mensajes con TypeMapper
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

    // RabbitTemplate para enviar/recibir mensajes
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
