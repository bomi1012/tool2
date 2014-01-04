/**
 * Created by Sebastian Walz
 * 26.04.2013 17:37:39
 */
package de.hska.awp.palaver2.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class Util {
	private static final String ENCODING = "UTF-8";
	private static final String ALGORITHM = "SHA-1";

	public Util() {
		
	}
	
	/**
	 * Konverter fuer DB
	 * 
	 * @param bool
	 * @return
	 */
	public static Integer convertBoolean(Boolean bool) {
		return (bool) ? 1 : 0;
	}
	
	public static Date getDate(int dayOfWeek , int dayOfMonth) {
		Date before = new Date();
        Calendar cal = Calendar.getInstance(Locale.GERMANY);
        cal.setTime(before);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        cal.add(Calendar.DAY_OF_MONTH, dayOfMonth); //+7 = 2 Woche	
		return cal.getTime();
	}
	

	/**
	 * Passwordverschluesselung
	 * 
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	@Deprecated
	public static byte[] getMD5(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] bytesOfMessage = password.getBytes(ENCODING);
		MessageDigest md = MessageDigest.getInstance(ALGORITHM);
		return md.digest(bytesOfMessage);
	}

	public static String encryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String sha1 = "";
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(password.getBytes("UTF-8"));
		sha1 = byteToHex(crypt.digest());

		return sha1;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
