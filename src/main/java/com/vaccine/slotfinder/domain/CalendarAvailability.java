package com.vaccine.slotfinder.domain;

import java.sql.Timestamp;
import java.util.List;

/**
 * Calendar Availability bean
 * @author 
 *
 */
public class CalendarAvailability {

	private Integer center_id;
	private String date;
	private Integer min_age_limit;
	private String vaccine;
	private List<String> slots;
	private Integer available_capacity_dose1;
	private Integer available_capacity_dose2;
	private String name;
	private Integer pincode;
	private String fee_type;
	private Timestamp detected_date_time;
	
	public Timestamp getDetected_date_time() {
		return detected_date_time;
	}
	public void setDetected_date_time(Timestamp detected_date_time) {
		this.detected_date_time = detected_date_time;
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
	public Integer getCenter_id() {
		return center_id;
	}
	public void setCenter_id(Integer center_id) {
		this.center_id = center_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getMin_age_limit() {
		return min_age_limit;
	}
	public void setMin_age_limit(Integer min_age_limit) {
		this.min_age_limit = min_age_limit;
	}
	public String getVaccine() {
		return vaccine;
	}
	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}
	public List<String> getSlots() {
		return slots;
	}
	public void setSlots(List<String> slots) {
		this.slots = slots;
	}
	public Integer getAvailable_capacity_dose1() {
		return available_capacity_dose1;
	}
	public void setAvailable_capacity_dose1(Integer available_capacity_dose1) {
		this.available_capacity_dose1 = available_capacity_dose1;
	}
	public Integer getAvailable_capacity_dose2() {
		return available_capacity_dose2;
	}
	public void setAvailable_capacity_dose2(Integer available_capacity_dose2) {
		this.available_capacity_dose2 = available_capacity_dose2;
	}
	
	public String toString() {
		StringBuilder strBldr = new StringBuilder();
		strBldr.append("\nPincode : " + pincode + ",");
		strBldr.append(" Center Name : "+name+",");
		strBldr.append(" Fee Type : "+fee_type);
		strBldr.append("\nAvailable Date : "+this.getDate());
		strBldr.append("\nAge : "+ this.getMin_age_limit()+"+,");
		strBldr.append(" Vaccine : "+ this.getVaccine());
		strBldr.append("\nDose 1 : "+ this.getAvailable_capacity_dose1());
		strBldr.append("\nDose 2 : "+ this.getAvailable_capacity_dose2());
		strBldr.append("\n\n");
		strBldr.append("https://selfregistration.cowin.gov.in/");
		return strBldr.toString();
	}
}
