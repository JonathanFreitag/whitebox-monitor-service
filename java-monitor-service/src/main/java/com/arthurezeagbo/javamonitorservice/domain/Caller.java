package com.arthurezeagbo.javamonitorservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Caller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private int pollingFrequency;
    private int graceTimeInMinutes;
    private LocalDateTime outageStartTime;
    private LocalDateTime outageEndTime;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "caller_service_table",
            joinColumns = {
                    @JoinColumn(name = "caller_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "service_id", referencedColumnName = "id")
            }
    )
    private Set<ServiceModel> serviceModels;

}