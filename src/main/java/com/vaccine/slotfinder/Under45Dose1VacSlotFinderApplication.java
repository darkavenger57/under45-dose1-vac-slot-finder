package com.vaccine.slotfinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Basic Spring Boot App
 * @author 
 *
 */
@SpringBootApplication
@EnableScheduling 
public class Under45Dose1VacSlotFinderApplication  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Under45Dose1VacSlotFinderApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Under45Dose1VacSlotFinderApplication.class, args);
		LOGGER.info("----- Started Vac Slot Finder -----");
	}
}
