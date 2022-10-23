package com.arthurezeagbo.javamonitorservice.repository;

import com.arthurezeagbo.javamonitorservice.domain.ServicePollingFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicePollingFrequencyRepository extends JpaRepository<ServicePollingFrequency,Integer> {
}
