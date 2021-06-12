package com.vaccine.slotfinder.service;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vaccine.slotfinder.dao.JdbcSlotFinderDaoRepository;
import com.vaccine.slotfinder.domain.CalendarAvailability;
import com.vaccine.slotfinder.domain.ReportingData;
import com.vaccine.slotfinder.util.DataFormatter;


/**
 * Data Service to fetch analytics data and stored slot finds
 * @author 
 *
 */

@Service
public class Under45DataService {

	@Autowired
	private JdbcSlotFinderDaoRepository jdbcSlotFinderDaoRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Under45DataService.class);
	
	/**
	 * Gets Reporting Data for a date(slot detection date) and age
	 * @param date
	 * @param age
	 * @return
	 */
	public List<ReportingData> getDailyStatsData(String date,String dose) {
		LOGGER.info("Inside Daily Stats Reporting Service ");
		return jdbcSlotFinderDaoRepository.findByDateAndDose(date,dose);
	}
	
	/**
	 * Save Slot Detected Info to DB
	 * @param ca
	 */
	public void saveSlotDetectedInfo(CalendarAvailability ca) {
		LOGGER.info("Inside Save of Slot Detection ");
		jdbcSlotFinderDaoRepository.save(ca);
	}
	/**
	 * Gets Daily Summary Data
	 * @param date
	 * @param dose
	 * @return
	 */
	public String getMiniDailySummaryStatsData(String date,String dose) {
		
		Map<String,List<Map<String,Object>>> map = jdbcSlotFinderDaoRepository.queryDailyMiniSummaryStats(date, dose);
		
		List<Map<String,Object>> timeOfDayList = map.get("TIMESLOT_OF_DAY");
        List<Map<String,Object>> pincodesDayList = map.get("PINCODE_OF_DAY");
        List<Map<String,Object>> centersDayList = map.get("CENTER_OF_DAY");
       
        String data = DataFormatter.formatToMiniSummaryData(timeOfDayList, pincodesDayList, centersDayList);
        
        LOGGER.info("Inside Wekkly Mini Stats Reporting ");
        return data;
 	}
}