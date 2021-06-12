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
	 * Predicate to check if availability is for Under 45 age group and dose 1 availability > LIMIT
	 * @return
	 */
	public static Predicate<CalendarAvailability> isUnder45AndDose1Available() {
		return p -> p.getMin_age_limit() == 18 && p.getAvailable_capacity_dose1() > DOSE_LIMIT_CHECK;
	}
	
}
