package com.vaccine.slotfinder.util;

import java.util.function.Predicate;
import com.vaccine.slotfinder.domain.CalendarAvailability;

/**
 * Basic Predicates used for filtering data
 * @author 
 *
 */
public class CalendarAvailabilityPredicates {
	
	private static int DOSE_LIMIT_CHECK = 5;
	/**
	 * Predicate to check if availability is for Under 45 age group
	 * @return
	 */
	public static Predicate<CalendarAvailability> isUnder45() {
		return p -> p.getMin_age_limit()==18;
	}
	
	/**
	 * Predicate to check if dose capacity(both) is > DOSE_LIMIT_CHECK
	 * @return
	 */
	public static Predicate<CalendarAvailability> isDoseCapacityGreaterThanLimit() {
		return p -> p.getAvailable_capacity_dose1()>DOSE_LIMIT_CHECK || p.getAvailable_capacity_dose2()>DOSE_LIMIT_CHECK ;
	}
	
	/**
	 * Predicate to check if dose is 2nd and COVISHIELD
	 * @return
	 */
	public static Predicate<CalendarAvailability> isCoviShieldAndSecondDose() {
		return p -> p.getVaccine().equals("COVISHIELD") && p.getAvailable_capacity_dose2()>1;
	}
}
