package com.vaccine.slotfinder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

/**
 * Sample class to try,test,run anything in a command line mode
 * @author 
 *
 */
//@SpringBootApplication
public class TestDBRunnerApplication implements CommandLineRunner{

	//@Autowired
	//private JdbcSlotFinderDaoRepository jdbcSlotFinderDaoRepository;
	
	public static void main(String args[]) {
	    SpringApplication.run(TestDBRunnerApplication.class, args);
	  }
	
	@Override
	public void run(String... args) throws Exception {
		
		
	}

}
