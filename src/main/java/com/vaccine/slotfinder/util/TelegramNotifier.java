package com.vaccine.slotfinder.util;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Notify users of Telegram Channel
 * @author 
 *
 */
@Component
public class TelegramNotifier {
	
    @Value("${spring.telegram.bot.key}")
    private String botToken;
    
    @Value("${spring.telegram.chatid}")
    private String chatId;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramNotifier.class);
 
    /**
     * Send message to Telegram Channel
     * @param message
     */
    public void sendMessage(String message)  {
    	
    	Map<String, String> urlParameters = new HashMap<String,String>();
    	urlParameters.put("chat_id", chatId);
    	urlParameters.put("text", message);
    	
    	String url = "https://api.telegram.org/"+botToken+"/sendMessage";
    	RestTemplate restTemplate = new RestTemplate();
    	
    	ResponseEntity<Object> response = restTemplate.postForEntity(url,urlParameters,Object.class);
    	if(response.getStatusCode().equals(HttpStatus.OK)) {
    		LOGGER.info("Slots Detected ! , Message Sent to Channel");
    	}
    }
}
