package com.arthurezeagbo.javamonitorservice.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterServiceDto {
    @NotNull
    private int id;
    private String name;
    @NotNull
    private String host;
    @NotNull
    private String port;
}
