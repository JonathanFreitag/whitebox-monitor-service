package com.arthurezeagbo.javamonitorservice.dto;

import com.arthurezeagbo.javamonitorservice.enums.StatusEnum;

import java.io.Serializable;
import java.util.List;

public record MessagePayload(String host, String port, List<String> email, StatusEnum status) implements Serializable {
}
