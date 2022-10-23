package com.arthurezeagbo.javamonitorservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicePollingFrequency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int serviceId;
    private int callerId;
    private int pollingFrequency;
}
