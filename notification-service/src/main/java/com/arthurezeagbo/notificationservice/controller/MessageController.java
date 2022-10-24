package com.arthurezeagbo.notificationservice.controller;

import com.arthurezeagbo.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/message")
public class MessageController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity viewMessages(){
        return ResponseEntity.ok(notificationService.viewMessages());
    }
}
