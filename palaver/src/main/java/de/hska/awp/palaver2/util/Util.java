/**
 * Created by Sebastian Walz
 * 26.04.2013 17:37:39
 */
package de.hska.awp.palaver2.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Util {

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
