package com.arthurezeagbo.javamonitorservice.domain;

import com.arthurezeagbo.javamonitorservice.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "service")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String port;
    private String host;
    private String name;
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.UP;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany(mappedBy = "serviceModels", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Caller> callers;
}
