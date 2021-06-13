package com.vaccine.slotfinder.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import com.vaccine.slotfinder.domain.CalendarAvailability;
import com.vaccine.slotfinder.domain.ReportingData;

/**
 * Basic JDBC Repo class for DB operations
 * @author 
 *
 */
@Repository
public class PostgreSqlJdbcSlotFinderDaoRepository implements SlotFinderDaoRepository {
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PostgreSqlJdbcSlotFinderDaoRepository.class);

    @Override
    public void save(CalendarAvailability ca)  {
    	
    	String calendarsql =  "insert into calendar_availability"
				+ " (center_id,available_date,min_age_limit,vaccine,available_capacity_dose1,available_capacity_dose2,detected_date_time,slots,name,pincode,fee_type)"
                + " values (:center_id,:available_date,:min_age_limit,:vaccine,:available_capacity_dose1,:available_capacity_dose2,"
                + "LOCALTIMESTAMP,:slots,:name,:pincode,:fee_type)";
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("center_id", ca.getCenter_id())
				.addValue("available_date", ca.getDate())
				.addValue("min_age_limit", ca.getMin_age_limit())
				.addValue("vaccine", ca.getVaccine())
				.addValue("available_capacity_dose1",ca.getAvailable_capacity_dose1())
				.addValue("available_capacity_dose2",ca.getAvailable_capacity_dose2())
				.addValue("slots", String.join(",", ca.getSlots()))
				.addValue("name", ca.getName())
				.addValue("pincode", ca.getPincode())
				.addValue("fee_type", ca.getFee_type());
		
		namedParameterJdbcTemplate.update(calendarsql, parameters);
    }
    
    /**
     * Gets Daily detailed data
     */
    @Override
    public List<ReportingData> findByDateAndDose(String date,String dose) {
    	
    	String doseFragment = dose.equals("dose1")?"available_capacity_dose1":"available_capacity_dose2";
    	
    	String sql = "select  to_char(to_date(available_date,'dd-MM-yyyy'),'dd-Mon-yyyy') as available_date,name as center_name,"+
    			"min_age_limit as age,vaccine,pincode,"
    			+ "(extract(hour from detected_date_time)*60 + extract(minute from detected_date_time)) as time_of_day"
    			+ ",available_capacity_dose1 as dose "+
    			"from calendar_availability where "+
    			"to_char(detected_date_time,'DD-MM-YYYY')=:reporting_date and min_age_limit=18 and "+doseFragment+">1"
    			+ " order by time_of_day";
    	
    	SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("reporting_date", date);
		
    	List<ReportingData> dataList = namedParameterJdbcTemplate.query(sql, parameters, new ReportingDataMapper(false));
    	LOGGER.info("Size : "+ dataList.size());
    	return dataList;
    }
    
	/*
	* Gets Daily Time Slot data
	*/
   @Override
   public List<ReportingData> findByDateAndDoseTimeSlot(String date,String dose) {
   	
   	String doseFragment = dose.equals("dose1")?"available_capacity_dose1":"available_capacity_dose2";
   	
   	
   	String sql= "select  to_char(to_date(available_date,'dd-MM-yyyy'),'dd-Mon-yyyy') as available_date,name as center_name, "+
		"min_age_limit as age,vaccine,pincode, "+
		"to_char(detected_date_time,'HH24:mm:ss') as time_of_day_str "+
		",available_capacity_dose1 as dose "+
		"from calendar_availability where "+
		"to_char(detected_date_time,'DD-MM-YYYY')=:reporting_date and min_age_limit=18 "+ 
		"and "+doseFragment+" > 1 "+
		 "order by time_of_day_str ";
   	
   	SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("reporting_date", date);
		
   	List<ReportingData> dataList = namedParameterJdbcTemplate.query(sql, parameters, new ReportingDataMapper(true));
   	LOGGER.info("Size : "+ dataList.size());
   	return dataList;
   }
    
    /**
     * Gets daily Summarized data
     */
    @Override
    public Map<String,List<Map<String,Object>>> findByDateAndDoseDailyMiniSummary(String date,String dose) {
    	
    	String doseFragment = dose.equals("dose1")?"available_capacity_dose1":"available_capacity_dose2";
    	
    	String timeOfDaySql= "select extract(hour from detected_date_time)as hour_of_day ,"+
        "sum(available_capacity_dose1) as count_detected "+
        "from calendar_availability where " +
		"to_char(detected_date_time,'DD-MM-YYYY')=:reporting_date and min_age_limit=18 "+ 
		"and "+doseFragment+">1"+
		"group by hour_of_day  order by count_detected desc limit 3 ";
        
        String pinCodeSql ="Select pincode, "+
        "sum(available_capacity_dose1) as count_detected "+
        "from calendar_availability where "+
		"to_char(detected_date_time,'DD-MM-YYYY')=:reporting_date and min_age_limit=18 "+ 
		"and "+doseFragment+">1"+
		"group by pincode  order by count_detected desc limit 3 ";
        
        String centerSql ="select name, "+
        "sum(available_capacity_dose1) as doses_detected "+
        "from calendar_availability where "+ 
		"to_char(detected_date_time,'DD-MM-YYYY')=:reporting_date and min_age_limit=18 "+ 
		"and "+doseFragment+">1"+ 
		"group by name  order by doses_detected desc limit 3 ";
        
        SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("reporting_date", date);
        
        List<Map<String,Object>> timeOfDayList = namedParameterJdbcTemplate.queryForList(timeOfDaySql, parameters);
        List<Map<String,Object>> pincodesDayList = namedParameterJdbcTemplate.queryForList(pinCodeSql, parameters);
        List<Map<String,Object>> centersDayList = namedParameterJdbcTemplate.queryForList(centerSql, parameters);
       
        Map<String,List<Map<String,Object>>> resultMap = new HashMap<String, List<Map<String,Object>>>();
        resultMap.put("TIMESLOT_OF_DAY", timeOfDayList);
        resultMap.put("PINCODE_OF_DAY", pincodesDayList);
        resultMap.put("CENTER_OF_DAY", centersDayList);

        return resultMap;
    }
    
    /**
     * Reporting Data Mapper
     * @author 
     *
     */
    class ReportingDataMapper implements RowMapper<ReportingData> {
    	
    	boolean timeOfDayStr = false;
    	
    	ReportingDataMapper(boolean timeOfDayFlg) {
    		this.timeOfDayStr = timeOfDayFlg;
    	}

    	@Override
    	public ReportingData mapRow(ResultSet rs, int rowNum) throws SQLException {
    	
    		ReportingData rd = new ReportingData();
    		rd.setAge(Integer.toString(rs.getInt("age")));
    		rd.setAvailable_date(rs.getString("available_date"));
    		rd.setCenter_name(rs.getString("center_name"));
    		rd.setDose(Integer.toString(rs.getInt("dose")));
    		rd.setPincode(Integer.toString(rs.getInt("pincode")));
    		if(!timeOfDayStr) {
    			rd.setTime_of_day(rs.getInt("time_of_day"));
    		}
    		else {
    			rd.setTime_of_day_str(rs.getString("time_of_day_str"));
    		}
    			
     		return rd;
    	}
    }
}
