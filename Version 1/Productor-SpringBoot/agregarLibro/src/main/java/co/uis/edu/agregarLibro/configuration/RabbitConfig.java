package co.uis.edu.agregarLibro.configuration;

import org.springframework.amqp.core.DirectExchange;
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

    // Constantes para el productor
    public static final String EXCHANGE_NAME = "libroExchange";
    public static final String COMMON_ROUTING_KEY = "addbookcommon.key";
    public static final String MALAGA_ROUTING_KEY = "addbookmalaga.key";
    public static final String SOCORRO_ROUTING_KEY = "addbooksocorro.key";


    // Declarar el exchange (DirectExchange)
    @Bean
    public DirectExchange libroExchange() {
        return new DirectExchange(EXCHANGE_NAME);
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

    // Convertidor de mensajes (mismo setup para que ambos compartan el formato)
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

    // RabbitTemplate para enviar mensajes
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
