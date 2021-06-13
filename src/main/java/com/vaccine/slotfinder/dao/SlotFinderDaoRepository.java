package com.vaccine.slotfinder.dao;

import java.util.List;
import java.util.Map;
import com.vaccine.slotfinder.domain.CalendarAvailability;
import com.vaccine.slotfinder.domain.ReportingData;

/**
 * Slot Finder DAO Repo interface
 * @author 
 *
 */
public interface SlotFinderDaoRepository {
	
	/**
	 * Save Calendar Info when slots are detected as available
	 * @param ca
	 */
	public void save(CalendarAvailability ca);
	
	/**
	 * Gets detected calendar availability Data for a given date 
	 * @param date
	 * 
	 * @return
	 */
	public List<ReportingData> findByDateAndDose(String date,String dose);
	
	
	/**
	 * Gets Daily Summary data
	 * @param date
	 * @param dose
	 * @return
	 */
	public Map<String,List<Map<String,Object>>> findByDateAndDoseDailyMiniSummary(String date,String dose);
}
