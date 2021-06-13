package com.vaccine.slotfinder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportingData {
	
	private String available_date;
	private String center_name;
	private String age;
	private String pincode;
	private Integer time_of_day;
	private String dose;
	private String time_of_day_str;
	
	public String getTime_of_day_str() {
		return time_of_day_str;
	}
	public void setTime_of_day_str(String time_of_day_str) {
		this.time_of_day_str = time_of_day_str;
	}
	public String getAvailable_date() {
		return available_date;
	}
	public void setAvailable_date(String available_date) {
		this.available_date = available_date;
	}
	public String getCenter_name() {
		return center_name;
	}
	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public Integer getTime_of_day() {
		return time_of_day;
	}
	public void setTime_of_day(Integer time_of_day) {
		this.time_of_day = time_of_day;
	}
	public String getDose() {
		return dose;
	}
	public void setDose(String dose) {
		this.dose = dose;
	}
}
