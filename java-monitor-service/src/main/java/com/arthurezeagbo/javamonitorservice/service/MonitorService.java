package com.arthurezeagbo.javamonitorservice.service;

import com.arthurezeagbo.javamonitorservice.dto.RegisterCallerDto;
import com.arthurezeagbo.javamonitorservice.dto.RegisterServiceDto;
import com.arthurezeagbo.javamonitorservice.dto.SubscribeToServicesDto;
import com.arthurezeagbo.javamonitorservice.dto.UpdateCallerDto;
import com.arthurezeagbo.javamonitorservice.response.ServiceResponse;

public interface MonitorService {

    ServiceResponse registerService(RegisterServiceDto request);
    ServiceResponse registerCaller(RegisterCallerDto request);
    void updateCaller(UpdateCallerDto request);
    void subscribeCallerToService(SubscribeToServicesDto request);
    ServiceResponse viewCallerDetail(final int id);
    ServiceResponse viewServices();

}
