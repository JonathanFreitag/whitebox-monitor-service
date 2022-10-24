package com.arthurezeagbo.notificationservice.dto;

import com.arthurezeagbo.notificationservice.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePayload implements Serializable {
    private String host;
    private String port;
    private List<String> email;
    private StatusEnum status;
}
