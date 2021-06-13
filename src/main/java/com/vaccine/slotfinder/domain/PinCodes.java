package com.vaccine.slotfinder.domain;

import java.util.Arrays;
import java.util.List;

/**
 * Static class where pincodes are provided.
 * Do remember :- 100 API calls in span of 5 mins from single IP address
 * These pincodes can be picked up from DB or Props file also.
 * 
 */
public class PinCodes {
	
	/** List of Pune pincodes with typical hospitals having doses available **/
	public static List<String> pinCodes = Arrays.asList("411001","411004","411007","411038","411057","411040","412115",
			"411028","411006","411005","411058","411040","411013","411011","411014");

}
