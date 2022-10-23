package com.arthurezeagbo.javamonitorservice.service.impl;

import com.arthurezeagbo.javamonitorservice.domain.Caller;
import com.arthurezeagbo.javamonitorservice.domain.ServiceModel;
import com.arthurezeagbo.javamonitorservice.domain.ServiceOutage;
import com.arthurezeagbo.javamonitorservice.domain.ServicePollingFrequency;
import com.arthurezeagbo.javamonitorservice.dto.*;
import com.arthurezeagbo.javamonitorservice.repository.CallerRepository;
import com.arthurezeagbo.javamonitorservice.repository.ServiceOutageRepository;
import com.arthurezeagbo.javamonitorservice.repository.ServicePollingFrequencyRepository;
import com.arthurezeagbo.javamonitorservice.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MonitorServiceImplTest {

    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private CallerRepository callerRepository;
    @Mock
    private ServiceOutageRepository outageRepository;
    @Mock
    private ServicePollingFrequencyRepository pollingFrequencyRepository;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private MonitorServiceImpl monitorService;
    private DateTimeFormatter dateTimeFormatter;

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    void shouldRegisterService(){
        //Given
        RegisterServiceDto registerServiceDto = new RegisterServiceDto();
        registerServiceDto.setId(1);
        registerServiceDto.setName("Notification");
        registerServiceDto.setHost("localhost");
        registerServiceDto.setPort("8085");

        ServiceModel service = new ServiceModel();
        service.setId(1);
        service.setName("Notification");
        service.setHost("localhost");
        service.setPort("8085");
        service.setCallers(new HashSet<>());

        //When
        when(mapper.map(any(),any())).thenReturn(service);
        when(serviceRepository.save(any())).thenReturn(service);
        monitorService.registerService(registerServiceDto);

        //Then
        verify(serviceRepository,times(1)).save(service);
        assertDoesNotThrow( () -> monitorService.registerService(registerServiceDto));
    }

    @Test
    void shouldRegisterCaller() {
        //Given
        RegisterCallerDto registerCallerDto = new RegisterCallerDto();
        registerCallerDto.setName("Ryan");
        registerCallerDto.setEmail("ryan@ymail.com");
        registerCallerDto.setGraceTimeInMinutes(5);
        registerCallerDto.setOutageStartTime(LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter),dateTimeFormatter));
        registerCallerDto.setOutageEndTime(LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter),dateTimeFormatter));

        Caller caller = new Caller(1,"Ryan","ryan.ymail.com", LocalDateTime.parse(LocalDateTime.now().
                        format(dateTimeFormatter), dateTimeFormatter), 10,null,null);

        //When
        when(callerRepository.save(any())).thenReturn(caller);
        when(mapper.map(any(),any())).thenReturn(caller);
        monitorService.registerCaller(registerCallerDto);

        //Then
        verify(callerRepository, times(1)).save(caller);
        assertDoesNotThrow( () -> monitorService.registerCaller(registerCallerDto));
    }

    @Test
    void shouldAddCallerToService() {
        //Given
        int callerId = 1;
        int serviceId = 2;
        Caller caller = new Caller(1,"Ryan","ryan.ymail.com", LocalDateTime.parse(LocalDateTime.now().
                format(dateTimeFormatter), dateTimeFormatter), 10,new HashSet<>(),null);
        ServiceModel service = new ServiceModel();
        service.setId(1);
        service.setName("Notification");
        service.setHost("localhost");
        service.setPort("8085");

        //When
        when(callerRepository.findById(anyInt())).thenReturn(Optional.of(caller));
        when(serviceRepository.findById(anyInt())).thenReturn(Optional.of(service));
        monitorService.registerCallerToService(callerId,serviceId);

        //Then
        verify(callerRepository).save(caller);
        assertDoesNotThrow( () -> monitorService.registerCallerToService(callerId,serviceId));

    }

    @Test
    void shouldRegisterServiceOutage() {
        //Given
        RegisterServiceOutageDto request = new RegisterServiceOutageDto();
        request.setServiceId(1);
        request.setCallerId(2);
        request.setStartTime(LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter),dateTimeFormatter));
        request.setEndTime(LocalDateTime.parse(LocalDateTime.now().plusMinutes(10).format(dateTimeFormatter),dateTimeFormatter));

        ServiceOutage serviceOutage = new ServiceOutage();
        serviceOutage.setServiceId(1);
        serviceOutage.setCaller(new Caller());
        serviceOutage.setStartTime(LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter),dateTimeFormatter));
        serviceOutage.setEndTime(LocalDateTime.parse(LocalDateTime.now().plusMinutes(10).format(dateTimeFormatter),dateTimeFormatter));

        Caller caller = new Caller(1,"Ryan","ryan.ymail.com", LocalDateTime.parse(LocalDateTime.now().
                format(dateTimeFormatter), dateTimeFormatter), 10,new HashSet<>(), Set.of(serviceOutage));

        //When
        when(mapper.map(any(),any())).thenReturn(serviceOutage);
        when(callerRepository.findById(anyInt())).thenReturn(Optional.of(caller));
        monitorService.registerServiceOutage(request);

        //Then
        verify(outageRepository,times(1)).save(any());
        assertDoesNotThrow( () -> monitorService.registerServiceOutage(request));
    }

    @Test
    void shouldRegisterServiceGraceTime() {
        //Given
        ServiceGraceTimeDto serviceGraceTimeDto = new ServiceGraceTimeDto();
        serviceGraceTimeDto.setGraceTimeInMinutes(10);
        serviceGraceTimeDto.setCallerId(1);
        Caller caller = new Caller(1,"Ryan","ryan.ymail.com", LocalDateTime.parse(LocalDateTime.now().
                format(dateTimeFormatter), dateTimeFormatter), 10,null,null);

        //When
        when(callerRepository.findById(anyInt())).thenReturn(Optional.of(caller));
        monitorService.registerServiceGraceTime(serviceGraceTimeDto);

        //Then
        verify(callerRepository).save(caller);
        assertDoesNotThrow(() -> monitorService.registerServiceGraceTime(serviceGraceTimeDto));
    }

    @Test
    void shouldRegisterServicePollingFrequency() {
        //Given
        RegisterServicePollingFrequencyDto registerServicePollingFrequencyDto = new RegisterServicePollingFrequencyDto();
        registerServicePollingFrequencyDto.setPollingFrequency(10);
        registerServicePollingFrequencyDto.setServiceId(1);
        registerServicePollingFrequencyDto.setCallerId(2);

        ServicePollingFrequency servicePollingFrequency = new ServicePollingFrequency();
        servicePollingFrequency.setPollingFrequency(10);
        servicePollingFrequency.setServiceId(1);
        servicePollingFrequency.setCallerId(2);
        servicePollingFrequency.setId(1);

        //When
        when(mapper.map(any(),any())).thenReturn(servicePollingFrequency);
        monitorService.registerServicePollingFrequency(registerServicePollingFrequencyDto);

        //Then
        verify(pollingFrequencyRepository).save(servicePollingFrequency);
        assertDoesNotThrow(() -> monitorService.registerServicePollingFrequency(registerServicePollingFrequencyDto));
    }
}