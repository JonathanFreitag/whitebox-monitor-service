package com.arthurezeagbo.javamonitorservice.repository;

import com.arthurezeagbo.javamonitorservice.domain.Caller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallerRepository extends JpaRepository<Caller,Integer> {
}
