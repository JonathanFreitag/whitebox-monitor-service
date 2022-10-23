package com.arthurezeagbo.notificationservice.service;

import com.arthurezeagbo.notificationservice.constant.Topics;
import com.arthurezeagbo.notificationservice.dto.MessagePayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaNotificationListenerProcessor implements NotificationListenerService {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(id = Topics.NOTIFICATION_TOPIC_ID, topics = Topics.NOTIFICATION_TOPIC)
    @Override
    public void listen(String payload) {
        try {
            MessagePayload messagePayload = objectMapper.readValue(payload,MessagePayload.class);
            /***
             * At this stage, you send the message payload to caller using any means OR
             * You publish back for another system to consume it
             */
            log.info(String.format("Message received  %s"),messagePayload.toString());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
