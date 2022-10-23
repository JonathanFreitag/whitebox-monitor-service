package com.arthurezeagbo.javamonitorservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterServicePollingFrequencyDto {
    private int serviceId;
    private int callerId;
    private int pollingFrequency;
}
