package com.arthurezeagbo.javamonitorservice.service;

public interface NotificationService {
    <T> void publish(String destination, T data);
}
