package edu.br.infnet.mstasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.br.infnet.mstasks.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RabbitMQConfigTest {

    @Test
    void messageConverter_shouldReturnJackson2JsonMessageConverter() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RabbitMQConfig.class);
        Jackson2JsonMessageConverter converter = context.getBean(Jackson2JsonMessageConverter.class);
        assertNotNull(converter);
        assertTrue(true);
        context.close();
    }
}
