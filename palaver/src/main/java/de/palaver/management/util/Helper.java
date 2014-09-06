package de.palaver.management.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class Helper {

	public Helper() { }
	
	public static String DateToString(String format, Date date) {
		if (date != null && StringUtils.isNotBlank(format)) {
			return new SimpleDateFormat(format).format(date);
		} 
		return null;
	}
	
	public static boolean isDouble(String value) {
	    try {
	        Double.parseDouble(value);
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return true;
	}
	
	public static Integer convertBoolean(Boolean bool) {
		return (bool) ? 1 : 0;
	}
}
