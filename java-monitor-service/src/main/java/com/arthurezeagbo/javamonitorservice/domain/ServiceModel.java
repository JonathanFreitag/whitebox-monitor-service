package com.arthurezeagbo.javamonitorservice.domain;

import com.arthurezeagbo.javamonitorservice.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "service",uniqueConstraints = {@UniqueConstraint(columnNames = {"host","port"})})
@NoArgsConstructor
@AllArgsConstructor
public class ServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "port")
    private String port;
    @Column(name = "host")
    private String host;
    private String name;
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.UP;
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @ManyToMany(mappedBy = "services", fetch = FetchType.EAGER)
    private Set<Caller> callers = new HashSet<>();


    public Set<Caller> getCallers() {
        return callers;
    }

}
