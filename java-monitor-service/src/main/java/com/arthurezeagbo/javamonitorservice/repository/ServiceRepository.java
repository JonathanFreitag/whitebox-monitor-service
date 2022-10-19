package com.arthurezeagbo.javamonitorservice.repository;

import com.arthurezeagbo.javamonitorservice.domain.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel,Integer> {
}
