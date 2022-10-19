package com.arthurezeagbo.javamonitorservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic notification(){
        return new NewTopic("notification",6,(short) 3);
    }

    @Value("${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapServer;

    @Bean
    public ProducerFactory<String, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(
                Map.of(
                        BOOTSTRAP_SERVERS_CONFIG, bootstrapServer,
                        BUFFER_MEMORY_CONFIG, 33554432,
                        KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                        VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
                )
        );
    }

    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
