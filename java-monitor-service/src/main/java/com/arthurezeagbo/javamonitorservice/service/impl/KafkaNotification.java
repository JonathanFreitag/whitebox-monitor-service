package com.arthurezeagbo.javamonitorservice.service.impl;

import com.arthurezeagbo.javamonitorservice.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
public class KafkaNotification implements NotificationService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public <T> void publish(String topic, T data){
        try {
            String message = objectMapper.writeValueAsString(data);
            kafkaTemplate.send(topic,message);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
