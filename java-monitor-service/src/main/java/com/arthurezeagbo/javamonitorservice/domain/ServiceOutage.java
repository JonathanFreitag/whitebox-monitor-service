package com.arthurezeagbo.javamonitorservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOutage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int serviceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "caller_id", referencedColumnName = "id")
    private Caller caller;
}
