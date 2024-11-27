package com.hamroDaraz.daraz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DarazApplication {

	public static void main(String[] args) {
		SpringApplication.run(DarazApplication.class, args);
	}

}
