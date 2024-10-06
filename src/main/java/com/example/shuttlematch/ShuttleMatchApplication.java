package com.example.shuttlematch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.shuttlematch.entity"})
@EnableJpaRepositories(basePackages = "com.example.shuttlematch.repository")
@EnableScheduling
public class ShuttleMatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShuttleMatchApplication.class, args);
	}

}


