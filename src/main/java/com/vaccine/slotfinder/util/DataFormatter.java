package com.vaccine.slotfinder.util;

import java.util.List;
import java.util.Map;

/**
 * Formats Data
 * @author 
 *
 */
public class DataFormatter {
	
	/**
     * Formats Data to summary String representation
     * @param timeOfDayList
     * @param pincodesDayList
     * @param centersDayList
     * @return
     */
    public static String formatToMiniSummaryData(List<Map<String,Object>> timeOfDayList,List<Map<String,Object>> pincodesDayList,List<Map<String,Object>> centersDayList) {
    	
    	StringBuilder strBldr = new StringBuilder();
    	strBldr.append("Mini Summary for today \n");
    	strBldr.append("----------------------\n");
        strBldr.append("Top 3 time slots Of Day (24 hr format):\n");
        for(Map<String, Object> m1: timeOfDayList) {
        	Double hourOfDay = (Double) m1.get("hour_of_day");
        	Double nextHour = hourOfDay+1;
        	String formattedHour = hourOfDay.toString()+"-"+nextHour.toString();
        	strBldr.append("["+formattedHour+"] ");
        }
        strBldr.append("\n");
        strBldr.append("Top 3 Pincodes Of Day:\n");
        for(Map<String, Object> m1: pincodesDayList) {
        	Integer pincode = (Integer) m1.get("pincode");
        	strBldr.append("["+Integer.toString(pincode)+"] ");
        }
        strBldr.append("\n");
        strBldr.append("Top 3 Centers Of Day :\n");
        for(Map<String, Object> m1: centersDayList) {
        	String center = (String) m1.get("name");
        	strBldr.append("["+center+"] ");
        }
        return strBldr.toString();
    }
}
