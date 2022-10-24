package com.arthurezeagbo.javamonitorservice;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT10S")
@SpringBootApplication
public class JavaMonitorServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(JavaMonitorServiceApplication.class, args);
	}

}
