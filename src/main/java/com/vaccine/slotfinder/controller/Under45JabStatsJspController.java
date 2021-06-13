package com.vaccine.slotfinder.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaccine.slotfinder.domain.ReportingData;
import com.vaccine.slotfinder.service.Under45DataService;

/**
 * Jab Controller which pulls data and hands it over to JSP
 * Note :- JSP used for easy/quick dev 
 * @author 
 *
 */
@Controller
public class Under45JabStatsJspController {

	private static final Logger LOGGER = LoggerFactory.getLogger(Under45JabStatsJspController.class);
	
	@Autowired
	private Under45DataService dataService;
	
	/**
	 * Validates if date format and dose string is correct and then pulls data for daily stats and hands it over to JSP
	 * @param date
	 * @param dose
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/under45stats/daily/detailedstats", method=RequestMethod.GET,produces = "application/json")
	public String getUnder45VacSlotsDailyDetailedStats(@RequestParam String date, @RequestParam String dose,Model map) {
		try {
				Date dt = new SimpleDateFormat("dd-MMM-yyyy").parse(date);
				
				if(dose.equals("dose1") || dose.equals("dose2")) {
				List<ReportingData> dataList = dataService.getDailyStatsData
						(new SimpleDateFormat("dd-MM-yyyy").format(dt),dose);
			
				ObjectMapper mapper = new ObjectMapper();
				String data = mapper.writeValueAsString(dataList);
				map.addAttribute("data",data);
			
				return "under45vacdailydetailedstats";
			}
			return "error";
		}
		catch(ParseException | JsonProcessingException dtp) {
			LOGGER.error(dtp.getMessage());
			return "error";
		}
	}
	
	@RequestMapping(value="/under45stats/daily/timeslotstats", method=RequestMethod.GET,produces = "application/json")
	public String getUnder45VacSlotsDailyTimeSlotStats(@RequestParam String date, @RequestParam String dose,Model map) {
		try {
				Date dt = new SimpleDateFormat("dd-MMM-yyyy").parse(date);
				
				if(dose.equals("dose1") || dose.equals("dose2")) {
				List<ReportingData> dataList = dataService.getDailyStatsDataByTimeslot
						(new SimpleDateFormat("dd-MM-yyyy").format(dt),dose);
			
				ObjectMapper mapper = new ObjectMapper();
				String data = mapper.writeValueAsString(dataList);
				map.addAttribute("data",data);
			
				return "under45vacdailytimeslotstats";
			}
			return "error";
		}
		catch(ParseException | JsonProcessingException dtp) {
			LOGGER.error(dtp.getMessage());
			return "error";
		}
	}
	
	/**
	 * Gets Daily Summary
	 * @param date
	 * @param dose
	 * @return
	 */
	@RequestMapping(value="/under45stats/daily/minisummary", method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public String getUnder45VacSlotsMiniDailySummaryStats(@RequestParam String date, @RequestParam String dose) {
		try {
				Date dt = new SimpleDateFormat("dd-MMM-yyyy").parse(date);
				if(dose.equals("dose1") || dose.equals("dose2")) {
					String data =dataService.getMiniDailySummaryStatsData
					(new SimpleDateFormat("dd-MM-yyyy").format(dt),dose);
				
				return data;
			}
			return "error";
		}
		catch(ParseException dtp) {
			LOGGER.error(dtp.getMessage());
			return "error";
		}
	}
}
