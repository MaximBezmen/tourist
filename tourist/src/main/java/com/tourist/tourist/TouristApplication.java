package com.tourist.tourist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.telegram.telegrambots.ApiContextInitializer;

@PropertySource("classpath:application.properties")
@SpringBootApplication
@EnableJpaAuditing
public class TouristApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(TouristApplication.class, args);
	}

}
