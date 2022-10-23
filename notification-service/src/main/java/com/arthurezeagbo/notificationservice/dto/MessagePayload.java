package com.arthurezeagbo.notificationservice.dto;

import com.arthurezeagbo.notificationservice.enums.StatusEnum;

import java.io.Serializable;
import java.util.List;

public record MessagePayload(String host, String port, List<String> email, StatusEnum status) implements Serializable {
}
