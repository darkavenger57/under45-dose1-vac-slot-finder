package com.vaccine.slotfinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.vaccine.slotfinder.controller.Under45JabStatsJspController;

/**
 * Basic Spring Boot App
 * @author 
 *
 */
@SpringBootApplication
@EnableScheduling 
public class VacSlotFinderApplication  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Under45JabStatsJspController.class);
	
	public static void main(String[] args) {
		SpringApplication.run(VacSlotFinderApplication.class, args);
		LOGGER.info("----- Started Vac Slot Finder -----");
	}
}
