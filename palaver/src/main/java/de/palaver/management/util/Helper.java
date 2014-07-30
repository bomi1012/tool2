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
}
