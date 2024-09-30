package com.example.shuttlematch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.shuttlematch.entity"})
public class ShuttleMatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShuttleMatchApplication.class, args);
	}

}
