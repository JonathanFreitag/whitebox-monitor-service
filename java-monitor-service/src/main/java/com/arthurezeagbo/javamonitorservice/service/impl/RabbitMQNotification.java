package com.arthurezeagbo.javamonitorservice.service.impl;

import com.arthurezeagbo.javamonitorservice.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQNotification implements NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbit.messaging.routing-key}")
    private String routingKey;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public <T> void publish(String queue, T data) {
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(data);
            rabbitTemplate.convertAndSend(queue,routingKey,payload);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
