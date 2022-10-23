package com.arthurezeagbo.javamonitorservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscribeToServicesDto {
    private int callerId;
    private int serviceId;
}
