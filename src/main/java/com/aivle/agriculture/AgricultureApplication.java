package com.aivle.agriculture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AgricultureApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgricultureApplication.class, args);
	}

}
