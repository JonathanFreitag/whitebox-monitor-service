package com.arthurezeagbo.javamonitorservice.controller;

import com.arthurezeagbo.javamonitorservice.dto.*;
import com.arthurezeagbo.javamonitorservice.response.ServiceResponse;
import com.arthurezeagbo.javamonitorservice.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/register-caller", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerCaller(@RequestBody@Valid RegisterCallerDto request){
        return ResponseEntity.ok(monitorService.registerCaller(request));
    }

    @PostMapping(path = "/{serviceId}/caller/{callerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity subscribeToService(@PathVariable(name = "serviceId") int serviceId,
                                                              @PathVariable(name = "callerId") int callerId){
        return ResponseEntity.ok(monitorService.registerCallerToService(callerId,serviceId));
    }


    @PostMapping(path = "/register-service-outage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@RequestBody @Valid RegisterServiceOutageDto request){
        return ResponseEntity.ok(monitorService.registerServiceOutage(request));
    }

    @PostMapping(path = "/register-polling-frequency", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@RequestBody @Valid RegisterServicePollingFrequencyDto request){
        return ResponseEntity.ok(monitorService.registerServicePollingFrequency(request));
    }

    @PostMapping(path = "/register-grace-time", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerServiceGraceTime(@RequestBody ServiceGraceTimeDto request){
        return ResponseEntity.ok(monitorService.registerServiceGraceTime(request));
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewServices(){
        return ResponseEntity.ok(monitorService.viewServices());
    }

    @GetMapping(path = "/callers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewCallers(){
        return ResponseEntity.ok(monitorService.viewCallers());
    }




}
