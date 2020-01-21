package com.ezypay.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class Tools {
	
	static HashMap<String,Integer> map = new HashMap<String,Integer>();
	
	Tools(){
		map.put("sunday", 1);
		map.put("monday", 2);
		map.put("tuesday", 3);
		map.put("wednesday", 4);
		map.put("thursday", 5);
		map.put("friday", 6);
		map.put("saturday", 7);
	}

	public static String getInvoiceDt(String type, String days, String start, String end) throws NumberFormatException, ParseException {
		
		StringBuilder rtn = new StringBuilder();

		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		
		for (Date dt : getDates(dateFormat.parse(start), dateFormat.parse(end))) {
			cal.setTime(dt);
			if ("weekly".equalsIgnoreCase(type)) {
				cal.setTime(dt);
				if (cal.get(Calendar.DAY_OF_WEEK) == Integer.valueOf(days)) {
					rtn.append(dateFormat.format(dt));
					rtn.append(",");
				}
			} else if ("monthly".equalsIgnoreCase(type)) {
				cal.setTime(dt);
				if (cal.get(Calendar.DAY_OF_MONTH) == Integer.valueOf(days)) {
					rtn.append(dateFormat.format(dt));
					rtn.append(",");
				}
			} else {
				rtn.append(dateFormat.format(dt));
				rtn.append(",");
			}
		}
		return rtn.toString();

	}

	public static List<Date> getDates(Date startDate, Date endDate) {
		List<Date> range = new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);

		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(endDate);

		while (calendar.before(endCalendar)) {
			Date result = calendar.getTime();
			range.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		return range;
	}

}
