package com.vaccine.slotfinder.scheduler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vaccine.slotfinder.domain.CalendarAvailability;
import com.vaccine.slotfinder.domain.Center;
import com.vaccine.slotfinder.domain.PinCodes;
import com.vaccine.slotfinder.service.Under45DataService;
import com.vaccine.slotfinder.util.CalendarAvailabilityPredicates;
import com.vaccine.slotfinder.util.TelegramNotifier;
import com.vaccine.slotfinder.util.VacSlotApiAccessBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring's task scheduled to be triggered at every given interval
 * Detected Slot data is stored in cache so that same data if becomes available frequently is ignored.
 * Stored elements in cache expire after 7/8 mins.
 * 
 * @author 
 *
 */
@Slf4j
@Component
public class Under45Dose1WeeklySlotFinderTask {
	
	/** Logger **/
	private static final Logger LOGGER = LoggerFactory.getLogger(Under45Dose1WeeklySlotFinderTask.class);
	
	/** Base URL to be utilized **/
	private String BASE_URL="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?";
	
	@Autowired
	private Under45DataService under45DataService;
	
	@Autowired
	private TelegramNotifier telegramNotifier;
	
	/** create a cache for centers to avoid repetitive messages **/
    private LoadingCache<Integer, String> cache = initCache();
       
	/**
	 * Init's Cache with some default props
	 * Change expiry time as suited
	 * @return
	 */
    private LoadingCache<Integer, String>  initCache() {
    	
    	cache = CacheBuilder.newBuilder()
    		       .maximumSize(1000) 
    		       .expireAfterWrite(7, TimeUnit.MINUTES)
    		       .build(new CacheLoader<Integer, String>() {
    		          @Override
    		          public String load(Integer center_id) throws Exception {
    		             return "--start--";
    		          } 
    		       });
    	return cache;
    }
	/**
	 * Run the Slot Finder Task every 20 second interval
	 * The Cron expression can be put in a props file also
	 */
    
    @Scheduled(cron = "*/18 * * * * *")
	public void invokeSlotFinder() {

    	processCenterList(getCenterList());
    	LOGGER.info("Cowin API invoked for Pincodes : "+PinCodes.pinCodes+"\n");
	}
	
	/**
	 * Gets Calendar Availability Info from Cowin API calls
	 * @return
	 */
	private List<Center> getCenterList() {
		
		VacSlotApiAccessBuilder vacApiCallBuilder = new VacSlotApiAccessBuilder();
		vacApiCallBuilder.withBaseUrl(BASE_URL);
		vacApiCallBuilder.withDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		vacApiCallBuilder.withPinCodes(PinCodes.pinCodes);
		vacApiCallBuilder.withRestTemplate(new RestTemplate());
		List<Center> centerList = vacApiCallBuilder.execute();
		return centerList;
	}
	/**
	 * Process Center List to get Available Vac Slots under 45 for a week
	 * @param centerList
	 */
	private void processCenterList(List<Center> centerList) {
		
		for(Center c: centerList) {
			c.getSessions().forEach(ca -> LOGGER.debug(ca.toString()));
			List<CalendarAvailability> filteredList = getUnder45AndDoseCapacityFilteredList(c);
			filteredList.forEach(ca-> {
				if(precheckOk(ca)) {
					LOGGER.info("Pre Check OK");
					telegramNotifier.sendMessage(ca.toString());
					saveToDb(ca);
					LOGGER.info(ca.toString()+"\n");
				}
			});
		}
	}
	
	/**
	 * Save Slot available Info to DB
	 * @param c
	 * @param ca
	 */
	
	private void saveToDb(CalendarAvailability ca) {
		under45DataService.saveSlotDetectedInfo(ca);
		LOGGER.info("Saved to db : "+ ca.getCenter_id());
	}
	
	/**
	 * Perform pre-check :-
	 * Entries are put in cache with eviction time 10 mins so the available doses of qty 1 are excluded
	 * Basically those that are not being consumed
	 * @param ca
	 * @return
	 */
	private boolean precheckOk(CalendarAvailability ca) {
		
		String storedCaString = cache.getIfPresent(ca.getCenter_id());
		
		if(storedCaString!=null) {
			String currentCaToString = ca.toString();
			
			if(storedCaString.trim().equals(currentCaToString.trim())) {
				LOGGER.debug("Excluded[Repeat Data in less than 8 min] Center id :"+ ca.getCenter_id()+" : "+ ca.getDate()+" : "+ca.getName());
				return false;
			}
			cache.put(ca.getCenter_id(), ca.toString());
			LOGGER.info("Inserted into Cache : " + ca.getCenter_id());
			return true;
		} 
		cache.put(ca.getCenter_id(), ca.toString());
		LOGGER.info("Inserted (first time) into cache: " + ca.getCenter_id());
		return true;
	}
	
	/**
	 * Get under45 and dose 1 > Limit filtered list
	 * @param c
	 * @return
	 */
	private List<CalendarAvailability> getUnder45AndDoseCapacityFilteredList(Center c) {
		
		List<CalendarAvailability> filteredList = c.getSessions().stream()
				.filter(CalendarAvailabilityPredicates.isUnder45AndDose1Available()).collect(Collectors.toList());
		
		return filteredList;
	}
	
	/**
	 * Print Cache keys
	 */
	@SuppressWarnings("unused")
	private void printCacheKeys() {
		cache.asMap().forEach((k, v) -> 
	    LOGGER.info("Cache Key = " + k ));
	}
}
