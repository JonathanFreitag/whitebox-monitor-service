package com.arthurezeagbo.javamonitorservice.repository;

import com.arthurezeagbo.javamonitorservice.domain.ServiceOutage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOutageRepository extends JpaRepository<ServiceOutage,Integer> {
}
