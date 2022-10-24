package com.arthurezeagbo.notificationservice.service;

import java.util.List;

public interface NotificationService<T> {
     void consume(T data);
     List<T> viewMessages();
}
