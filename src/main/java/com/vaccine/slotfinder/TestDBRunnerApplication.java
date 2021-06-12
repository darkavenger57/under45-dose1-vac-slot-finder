package com.vaccine.slotfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaccine.slotfinder.scheduler.Under45Dose1WeeklySlotFinderTask;

/**
 * Sample class to try,test,run anything in a command line mode
 * @author 
 *
 */
//@SpringBootApplication
public class TestDBRunnerApplication implements CommandLineRunner{

	//@Autowired
	//private JdbcSlotFinderDaoRepository jdbcSlotFinderDaoRepository;
	
	@Autowired
	private Under45Dose1WeeklySlotFinderTask weeklySlotFinderTask;
	
	public static void main(String args[]) {
	    SpringApplication.run(TestDBRunnerApplication.class, args);
	  }
	
	@Override
	public void run(String... args) throws Exception {
		weeklySlotFinderTask.invokeSlotFinder();
		
	}

}
