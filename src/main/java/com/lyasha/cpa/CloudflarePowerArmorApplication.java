package com.lyasha.cpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CloudflarePowerArmorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudflarePowerArmorApplication.class, args);
	}

}
