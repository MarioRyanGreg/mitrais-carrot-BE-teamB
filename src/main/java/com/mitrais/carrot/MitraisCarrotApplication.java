package com.mitrais.carrot;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

/**
 * THis is a Spring Boot Application serving data for the Mitrais Carrot
 * Application
 */
@EntityScan(basePackageClasses = { MitraisCarrotApplication.class, Jsr310JpaConverters.class })
public class MitraisCarrotApplication {

	/**
	 * init method to set the time zone for the database
	 */
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * main static method
	 * 
	 * @param args Array string arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(MitraisCarrotApplication.class, args);
	}
}
