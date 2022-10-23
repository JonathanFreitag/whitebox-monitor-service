package com.arthurezeagbo.javamonitorservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Caller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private LocalDateTime currentGraceTime;
    private long graceTimeInMinutes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "caller_service",
            joinColumns = @JoinColumn(name = "caller_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id")
    )
    private Set<ServiceModel> services = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "caller", fetch = FetchType.EAGER)
    private Set<ServiceOutage> serviceOutages = new HashSet<>();

    public Set<ServiceModel> getServices() {
        return services;
    }

    public void addService(ServiceModel service){
        this.services.add(service);
    }

    public Set<ServiceOutage> getServiceOutages() {
        return serviceOutages;
    }

}