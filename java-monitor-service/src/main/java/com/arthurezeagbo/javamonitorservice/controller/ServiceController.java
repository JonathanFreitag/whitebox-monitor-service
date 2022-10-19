package com.arthurezeagbo.javamonitorservice.controller;

import com.arthurezeagbo.javamonitorservice.dto.RegisterServiceDto;
import com.arthurezeagbo.javamonitorservice.response.ServiceResponse;
import com.arthurezeagbo.javamonitorservice.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/service")
public class ServiceController {

    private final MonitorService monitorService;

    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> registerService(@RequestBody @Valid RegisterServiceDto request){
        return ResponseEntity.ok(monitorService.registerService(request));
    }


}
