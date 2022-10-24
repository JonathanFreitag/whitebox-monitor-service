package com.arthurezeagbo.javamonitorservice.service;

import com.arthurezeagbo.javamonitorservice.constant.MessageDestination;
import com.arthurezeagbo.javamonitorservice.domain.Caller;
import com.arthurezeagbo.javamonitorservice.domain.ServiceModel;
import com.arthurezeagbo.javamonitorservice.domain.ServiceOutage;
import com.arthurezeagbo.javamonitorservice.dto.MessagePayload;
import com.arthurezeagbo.javamonitorservice.repository.CallerRepository;
import com.arthurezeagbo.javamonitorservice.repository.ServiceOutageRepository;
import com.arthurezeagbo.javamonitorservice.repository.ServiceRepository;
import com.sun.xml.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MonitorServerNotificationProcessor {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private CallerRepository callerRepository;
    @Autowired
    ServiceOutageRepository outageRepository;
    @Autowired
    private NotificationService notificationService;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Scheduled(cron = "0/5 * * * * *")
    @SchedulerLock(
            name = "TASK_SCHEDULER_MONITOR_SERVICE",
            lockAtLeastFor = "PT5S",
            lockAtMostFor = "PT10S"
    )
    public void process(){
        ///TODO  change implementation to read from redis due to performance
        List<ServiceModel> services = serviceRepository.findAll();
        Map<ServiceModel, Set<Caller>> callerServiceMap = new HashMap<>();
        /**
         * Iterate through the services and callers and put them in a map
         */
        services.stream()
                .filter(s -> s.getCallers().size() != 0)
                .forEach(service -> {
                     callerServiceMap.put(service,service.getCallers());
                });

        callerServiceMap.forEach((service, callers) -> {
            List<Caller> mailList = new ArrayList<>();
            callers.parallelStream()
                    .forEach(caller ->{
                        /**
                         * Filter service with a planned service outage for a caller
                         */
                        Optional<ServiceOutage> serviceOutage = caller.getServiceOutages().stream()
                                .filter(outage -> outage == null || outage.getServiceId() == service.getId())
                                .findAny();

                        String currentDateTimeString = LocalDateTime.now().format(dateTimeFormatter);
                        LocalDateTime now = LocalDateTime.parse(currentDateTimeString,dateTimeFormatter);

                        if(serviceOutage.isPresent()){
                            processCallerWithServiceOutage(serviceOutage.get() ,caller, service, services, now, mailList);
                        }else{
                            processCallerWithoutServiceOutage(caller, service, services, now,mailList);
                        }
                    });
            /**
             * Send notification to caller
             */
            notifyCaller(service, mailList);
        });
    }

    private void notifyCaller(ServiceModel s, List<Caller> callers){
        Map<Integer, MessagePayload> payloadMap = new HashMap<>();
        if(callers.size() > 0){
            List<String> emails = callers.stream().map(c -> c.getEmail()).collect(Collectors.toList());
            MessagePayload payload = new MessagePayload(s.getHost(),s.getPort(),emails,s.getStatus());
            payloadMap.put(s.getId(),payload);
            notificationService.publish(MessageDestination.NOTIFICATION,payload);
            System.out.println();
            System.out.println("Service  "+s.getHost()+"  Port  "+s.getPort()+"   is "+s.getStatus()+"! Sending message to >>>>|||||| \t"+emails);
            System.out.println();
        }
    }

    private String getHost(ServiceModel service, List<ServiceModel> serviceList){
        return serviceList.get(serviceList.indexOf(service)).getHost();
    }

    private int getPort(ServiceModel service, List<ServiceModel> serviceList){
        return Integer.parseInt(serviceList.get(serviceList.indexOf(service)).getPort());
    }

    private void updateCaller(Caller caller){
        callerRepository.save(caller);
    }

    private void processCallerWithoutServiceOutage(Caller caller, ServiceModel service, List<ServiceModel> services, LocalDateTime now, List<Caller> mailList){
        int port = getPort(service,services);
        String localhost = getHost(service,services);
        try(Socket ignored = new Socket(localhost, port)){
            if(caller.getCurrentGraceTime() == null || caller.getCurrentGraceTime().isBefore(now)){
                mailList.add(caller);
            }
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            if(caller.getCurrentGraceTime() == null){
                caller.setCurrentGraceTime(LocalDateTime.now().plusMinutes(caller.getGraceTimeInMinutes()));
                updateCaller(caller);
            }else{
                if(caller.getCurrentGraceTime().isBefore(now)){
                    mailList.add(caller);
                }
            }
        }

    }

    private void processCallerWithServiceOutage(ServiceOutage serviceOutage , Caller caller, ServiceModel s, List<ServiceModel> services, LocalDateTime now, List<Caller> mailList){
        if( now.isBefore(serviceOutage.getStartTime())|| now.isAfter(serviceOutage.getEndTime()))
        {
            int port = getPort(s,services);
            String localhost = getHost(s,services);

            try(Socket soc =new Socket(localhost, port)){
                if(soc.isConnected()){
                    if(caller.getCurrentGraceTime() == null || caller.getCurrentGraceTime().isBefore(now)){
                        mailList.add(caller);
                    }
                }
            } catch (UnknownHostException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                if(caller.getCurrentGraceTime() == null){
                    caller.setCurrentGraceTime(LocalDateTime.now().plusMinutes(caller.getGraceTimeInMinutes()));
                    updateCaller(caller);
                }else{
                    if(caller.getCurrentGraceTime().isBefore(now)){
                        mailList.add(caller);
                    }
                }
            }
        }
    }

}


