package com.arthurezeagbo.javamonitorservice.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceResponse<T> {

    private T data;
    private String message;

    public ServiceResponse(String message, T data){
        this.message = message;
        this.data = data;
    }

    public ServiceResponse(T data){
        this("Successful",data);
    }
}
