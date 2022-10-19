package com.arthurezeagbo.javamonitorservice.service.impl;

import com.arthurezeagbo.javamonitorservice.domain.ServiceModel;
import com.arthurezeagbo.javamonitorservice.dto.RegisterServiceDto;
import com.arthurezeagbo.javamonitorservice.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MonitorServiceImplTest {

    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private MonitorServiceImpl monitorService;


    @Test
    void shouldRegisterService(){
        RegisterServiceDto registerServiceDto = new RegisterServiceDto();
        registerServiceDto.setName("Notification");
        registerServiceDto.setHost("localhost");
        registerServiceDto.setPort("8085");

        ServiceModel service = new ServiceModel();
        service.setId(1);
        service.setName("Notification");
        service.setHost("localhost");
        service.setPort("8085");

        when(serviceRepository.save(any()))
                .thenReturn(service);
        when(mapper.map(any(),any()))
                .thenReturn(service);

        monitorService.registerService(registerServiceDto);
        verify(serviceRepository, times(1)).save(service);
    }


    @Test
    void registerCaller() {
    }

    @Test
    void updateCaller() {
    }

    @Test
    void subscribeCallerToService() {
    }

    @Test
    void viewCallerDetail() {
    }

    @Test
    void viewServices() {
    }
}