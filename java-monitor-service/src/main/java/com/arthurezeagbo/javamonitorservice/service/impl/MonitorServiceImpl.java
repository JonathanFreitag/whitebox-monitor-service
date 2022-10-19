package com.arthurezeagbo.javamonitorservice.service.impl;

import com.arthurezeagbo.javamonitorservice.domain.ServiceModel;
import com.arthurezeagbo.javamonitorservice.dto.RegisterCallerDto;
import com.arthurezeagbo.javamonitorservice.dto.RegisterServiceDto;
import com.arthurezeagbo.javamonitorservice.dto.SubscribeToServicesDto;
import com.arthurezeagbo.javamonitorservice.dto.UpdateCallerDto;
import com.arthurezeagbo.javamonitorservice.repository.ServiceRepository;
import com.arthurezeagbo.javamonitorservice.response.ServiceResponse;
import com.arthurezeagbo.javamonitorservice.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitorServiceImpl implements MonitorService {

    Logger log = LoggerFactory.getLogger(MonitorServiceImpl.class);
    private final String ERROR_MESSAGE = "ERROR OCCURRED: %s";

    private final ServiceRepository serviceRepository;
    private final ModelMapper mapper;


    @Override
    public ServiceResponse registerService(RegisterServiceDto request) {
        try{
            ServiceModel serviceModel = mapper.map(request,ServiceModel.class);
            return new ServiceResponse(serviceRepository.save(serviceModel));
        }catch (Exception ex){
            log.info(ERROR_MESSAGE,ex.getMessage());
            return new ServiceResponse(ERROR_MESSAGE,ex.getMessage());
        }
    }

    @Override
    public ServiceResponse registerCaller(RegisterCallerDto request) {
        return null;
    }

    @Override
    public void updateCaller(UpdateCallerDto request) {

    }

    @Override
    public void subscribeCallerToService(SubscribeToServicesDto request) {

    }

    @Override
    public ServiceResponse viewCallerDetail(int id) {
        return null;
    }

    @Override
    public ServiceResponse viewServices() {
        return null;
    }
}
