package com.vaccine.slotfinder.scheduler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.vaccine.slotfinder.service.Under45DataService;
import com.vaccine.slotfinder.util.TelegramNotifier;

/**
 * Triggers task at scheduled time of day
 * @author 
 *
 */
@Component
public class DailyMiniSummaryTaskScheduler {
	
	/** Logger **/
	private static final Logger LOGGER = LoggerFactory.getLogger(WeeklySlotFinderTask.class);	
	
	/** Data Service **/
	@Autowired
	private Under45DataService under45DataService;
	
	/** Telegram notifier **/
	@Autowired
	private TelegramNotifier telegramNotifier;
	
	/**
	 * Cron trigger to send mini summary telegram message at 21:00 every day
	 */
	@Scheduled(cron = "0 10 21 * * *")
	public void sendDailyMiniSummary() {
	 
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	    String miniSummary = under45DataService.getMiniDailySummaryStatsData(date, "dose1");
	    telegramNotifier.sendMessage(miniSummary);
	    LOGGER.info("Mini Summary for : "+date+" sent!");
	}
}
