package com.vaccine.slotfinder.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Response bean to collect API response
 * @author 
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SlotFinderResponse {
	
	private List<Center> centers;

	public List<Center> getCenters() {
		return centers;
	}
	public void setCenters(List<Center> centers) {
		this.centers = centers;
	}
}
