package com.vaccine.slotfinder.domain;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Center bean to collect basic center information
 * @author 
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Center {
	
	private Integer center_id;
	private String name;
	private Integer pincode;
	private String fee_type;
	private Timestamp detected_time_of_day;
    private List<CalendarAvailability> sessions;
    
	public List<CalendarAvailability> getSessions() {
		return sessions;
	}
	public void setSessions(List<CalendarAvailability> sessions) {
		this.sessions = sessions;
		
		for(CalendarAvailability ca: this.sessions) {
			copyProps(this, ca);
		}
	}
	
	public Timestamp getDetected_time_of_day() {
		return detected_time_of_day;
	}
	public void setDetected_time_of_day(Timestamp detected_time_of_day) {
		this.detected_time_of_day = detected_time_of_day;
	}
	public Integer getCenter_id() {
		return center_id;
	}
	public void setCenter_id(Integer center_id) {
		this.center_id = center_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	
	/**
	 * Copy basic props to CalendarAvailability object for persistence
	 * @param c
	 * @param ca
	 */
	private void copyProps(Center c,CalendarAvailability ca) {
		ca.setCenter_id(c.getCenter_id());
		ca.setName(c.getName());
		ca.setFee_type(c.getFee_type());
		ca.setPincode(c.getPincode());
	}
}
