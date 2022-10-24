package com.arthurezeagbo.javamonitorservice.service.impl;

import com.arthurezeagbo.javamonitorservice.domain.*;
import com.arthurezeagbo.javamonitorservice.dto.*;
import com.arthurezeagbo.javamonitorservice.repository.*;
import com.arthurezeagbo.javamonitorservice.response.ServiceResponse;
import com.arthurezeagbo.javamonitorservice.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitorServiceImpl implements MonitorService {

    Logger log = LoggerFactory.getLogger(MonitorServiceImpl.class);
    private final String NOT_FOUND = "NOT FOUND";
    private final String ERROR_MESSAGE = "ERROR OCCURRED: %s";
    private final String NULL_INPUT_REQUEST = "NULL REQUEST NOT ALLOWED: %s";

    private final CallerRepository callerRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceOutageRepository outageRepository;
    private final ServicePollingFrequencyRepository pollingFrequencyRepository;

    private final ModelMapper mapper;
    private JdbcTemplate jdbcTemplate;


    @Override
    public ServiceResponse registerService(RegisterServiceDto request) {
        if(request == null)    return new ServiceResponse(NULL_INPUT_REQUEST,request);
        try{
            ServiceModel serviceModel = mapper.map(request,ServiceModel.class);
            serviceRepository.save(serviceModel);
            return new ServiceResponse(null);
        }
        catch (Exception ex){
            log.info(ERROR_MESSAGE,ex.getMessage());
            return new ServiceResponse(String.format(ERROR_MESSAGE,ex.getMessage()),null);
        }
    }

    @Override
    public ServiceResponse registerCaller(RegisterCallerDto request) {
        if(request == null)    return new ServiceResponse(NULL_INPUT_REQUEST, request);
        try{
            Caller caller = mapper.map(request,Caller.class);
            return new ServiceResponse(callerRepository.save(caller));
        }
        catch (Exception ex){
            log.info(ERROR_MESSAGE,ex.getMessage());
            return new ServiceResponse(String.format(ERROR_MESSAGE,ex.getMessage()),ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ServiceResponse registerCallerToService(int callerId, int serviceId) {

        try {
            Optional<Caller> caller = callerRepository.findById(callerId);
            Optional<ServiceModel> service = serviceRepository.findById(serviceId);
            if(caller.isPresent() && service.isPresent()) {
                caller.get().addService(service.get());
                return new ServiceResponse(callerRepository.save(caller.get()));
            }
        }
        catch (Exception ex){
            log.info(String.format(ERROR_MESSAGE,ex.getMessage()));
            return new ServiceResponse(String.format(ERROR_MESSAGE,ex.getMessage()),ex.getMessage());
        }
        return new ServiceResponse("Caller or service not found",null);
    }


    @Override
    @Transactional
    public ServiceResponse registerServiceOutage(RegisterServiceOutageDto request) {
        if(request == null)    return new ServiceResponse(NULL_INPUT_REQUEST,request);
        ServiceOutage serviceOutage = mapper.map(request,ServiceOutage.class);
        try {

            Optional<Caller> caller = callerRepository.findById(request.getCallerId());
            if(caller.isPresent()){
                serviceOutage.setCaller(caller.get());
                return new ServiceResponse(outageRepository.save(serviceOutage));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ServiceResponse(String.format(ERROR_MESSAGE,e.getMessage()),e.getMessage());
        }
        return new ServiceResponse(NOT_FOUND,null);
    }

    @Override
    @Transactional
    public ServiceResponse registerServiceGraceTime(ServiceGraceTimeDto request) {
        try {
            Optional<Caller> caller = callerRepository.findById(request.getCallerId());
            if(caller.isPresent()){
                caller.get().setGraceTimeInMinutes(request.getGraceTimeInMinutes());
                return new ServiceResponse(callerRepository.save(caller.get()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ServiceResponse(String.format(ERROR_MESSAGE,e.getMessage()),e);
        }
        return new ServiceResponse(null);
    }

    @Override
    public ServiceResponse registerServicePollingFrequency(RegisterServicePollingFrequencyDto request) {
        if(request == null)    return new ServiceResponse(NULL_INPUT_REQUEST,request);
        ServicePollingFrequency pollingFrequency = mapper.map(request, ServicePollingFrequency.class);
        return new ServiceResponse(pollingFrequencyRepository.save(pollingFrequency));
    }

    @Override
    public ServiceResponse viewServices() {
        return new ServiceResponse(serviceRepository.findAll());
    }

    @Override
    public ServiceResponse viewCallers() {
        return new ServiceResponse(callerRepository.findAll());
    }


}
