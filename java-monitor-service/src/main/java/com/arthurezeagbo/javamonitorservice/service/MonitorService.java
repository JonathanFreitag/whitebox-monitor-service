package com.arthurezeagbo.javamonitorservice.service;

import com.arthurezeagbo.javamonitorservice.dto.*;
import com.arthurezeagbo.javamonitorservice.response.ServiceResponse;

public interface MonitorService {

    ServiceResponse registerService(RegisterServiceDto request);
    ServiceResponse registerCaller(RegisterCallerDto request);
    ServiceResponse registerCallerToService(int callerId, int serviceId);

    ServiceResponse registerServiceOutage(RegisterServiceOutageDto request);
    ServiceResponse registerServiceGraceTime(ServiceGraceTimeDto request);
    ServiceResponse registerServicePollingFrequency(RegisterServicePollingFrequencyDto request);

    ServiceResponse viewServices();
    ServiceResponse viewCallers();

}
