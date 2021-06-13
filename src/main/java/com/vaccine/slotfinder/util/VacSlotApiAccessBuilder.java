package com.vaccine.slotfinder.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaccine.slotfinder.domain.Center;
import com.vaccine.slotfinder.domain.PinCodes;
import com.vaccine.slotfinder.domain.SlotFinderResponse;

/**
 * Minimal Builder class to build the API Setup API access and invoke the API
 * The API invoked in calendarByPin - This gives data for today + 7 days
 *  
 *
 */
public class VacSlotApiAccessBuilder {
	
	String date;
	String url;
	List<String> pincodes;
	RestTemplate restTemplate;
	String baseUrl;
	
	/** Logger **/
	private static final Logger LOGGER = LoggerFactory.getLogger(VacSlotApiAccessBuilder.class);
	
	/**
	 * Stores date
	 * @param date
	 */
	public void withDate(String date) {
		this.date=date;
	}
	
	/**
	 * Uses base URL
	 * @param baseUrl
	 */
	public void withBaseUrl(String baseUrl) {
		this.baseUrl=baseUrl;
	}
	/**
	 * Uses pincodes
	 * @param pincodes
	 */
	public void withPinCodes(List<String> pincodes) {
		this.pincodes=pincodes;
	}
	/**
	 * Uses Rest Template
	 * @param restTemplate
	 */
	public void withRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	/**
	 * Executes REST API calls to get the slot data for today + 7 days for given Pincodes
	 * @return
	 */
	public List<Center> execute() {
		
		List<Center> masterCenterList = new ArrayList<Center>();
		for(String pincode :pincodes) {
			String url = buildUrl(pincode,date,baseUrl);
			ResponseEntity<SlotFinderResponse> response =  restTemplate.getForEntity(url, SlotFinderResponse.class);
			LOGGER.debug("Cowin API URL invoked :"+ url);
			if(response.getStatusCode().equals(HttpStatus.OK)) {
				masterCenterList.addAll(response.getBody().getCenters());
			}
		}
		
		LOGGER.info("Cowin API invoked for Pincodes : "+PinCodes.pinCodes+"\n");
		return masterCenterList; 
	}
	
	/**
	 * Test method to load from file
	 * @return
	 */
	private SlotFinderResponse loadFromFile() {
		
		SlotFinderResponse slotFinderResponse = null;
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			File file = new ClassPathResource("sample-response.json").getFile();
			slotFinderResponse = objectMapper.readValue(file, SlotFinderResponse.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return slotFinderResponse;
	}
	
	/***
	 * Build the URL to be called
	 * @param pincode
	 * @param date
	 * @return
	 */
	private String  buildUrl(String pincode,String date,String baseUrl) {
		
		StringBuilder apiEndPointBuilder = new StringBuilder();
		String apiEndPoint = "pincode="+pincode+"&"+"date="+date;
		apiEndPointBuilder.append(baseUrl);
		apiEndPointBuilder.append(apiEndPoint);
		return apiEndPointBuilder.toString();
	}

}
