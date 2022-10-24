package com.arthurezeagbo.notificationservice.service.impl;

import com.arthurezeagbo.notificationservice.dto.MessagePayload;
import com.arthurezeagbo.notificationservice.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RabbitNotificationService implements NotificationService<MessagePayload> {

    private List<MessagePayload> messagePayloadList = new ArrayList<>();

    @Override
    public void consume(MessagePayload data) {
        if(messagePayloadList.contains(data)){
            messagePayloadList.remove(data);
        }
        messagePayloadList.add(data);
    }

    @Override
    public  List<MessagePayload> viewMessages() {
        return  messagePayloadList;
    }


}
