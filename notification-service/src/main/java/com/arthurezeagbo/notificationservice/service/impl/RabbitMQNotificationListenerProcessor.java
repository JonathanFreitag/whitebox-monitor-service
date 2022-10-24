package com.arthurezeagbo.notificationservice.service.impl;

import com.arthurezeagbo.notificationservice.dto.MessagePayload;
import com.arthurezeagbo.notificationservice.service.NotificationListenerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQNotificationListenerProcessor implements NotificationListenerService {

    private static final String NOTIFICATION_QUEUE = "notification";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitNotificationService notificationService;

    @RabbitListener(queues = NOTIFICATION_QUEUE)
    @Override
    public void listen(String message) {
        try {
            MessagePayload messagePayload = objectMapper.readValue(message,MessagePayload.class);
            /***
             * At this stage, you send the message payload to caller using any means OR
             * You publish back for another system to consume it
             */
            notificationService.consume(messagePayload);
            log.info(messagePayload.toString());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }



}
