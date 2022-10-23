package com.arthurezeagbo.javamonitorservice.dto;

import lombok.Data;

@Data
public class ServiceGraceTimeDto {
    private int callerId;
    private int graceTimeInMinutes;
}
