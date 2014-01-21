package de.hska.awp.palaver2.emailversand;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;

import org.apache.commons.codec.binary.Base64;

import de.hska.awp.palaver2.data.MailDAO;
import de.palaver.dao.email.MailModel;

/**
 * Klass wird für das Emailversand benötigt
 * 
 * @author Mihail Boehm
 */
public class Mail extends MailDAO {
	private static Mail instance = null;
	MailModel mail;
	byte[] input;
	byte[] keyBytes;

	public Mail() {
		super();
	}

	public static Mail getInstance() {
		if (instance == null) {
			instance = new Mail();
		}
		return instance;
	}

	/**
	 * @param to
	 *            Empfanger
	 * @param subject
	 *            Thema
	 * @param message
	 *            Text
	 */
	public boolean EmailVersand(String to, String subject, String message,
			String anhang) {
		boolean ergebnis = false;
		if (anhang == null || anhang == "") {
			try {
				MailActions.sendOhneAnhang(MailAccounts.NACHRICHT, to, subject,
						message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print(e.toString());
			} 
			ergebnis = true;
		} else {
			try {
				MailActions.sendMitAnhang(MailAccounts.NACHRICHT, to, subject,
						message, anhang);
				ergebnis = true;
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		return ergebnis;
	}

	/**
	 * bekommt entschlüsseltes Passwort
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public String Password(String filter) throws Exception {
		mail = super.getMailByEnum(filter);
		return Enschlusseln(mail);
	}

	/**
	 * !! Diese Methode nur dann verwenden, um Passwort zu verschlüsseln, wenn
	 * ein neues Account erstellt wird !!
	 * 
	 * @param plainText
	 *            Passwort
	 * @param schlussel
	 *            Schlussel, um Passwort später zu entschlüsseln
	 * @param getEnum
	 *            für Enum
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void Verschlusseln(String plainText, String schlussel,
			String getEnum) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		input = plainText.getBytes();
		keyBytes = schlussel.getBytes();
		SecretKeySpec key = new SecretKeySpec(keyBytes, "ARC4");
		Cipher cipher = Cipher.getInstance("ARC4", "BC");
		byte[] cipherText = new byte[input.length];
		cipher.init(Cipher.ENCRYPT_MODE, key);
		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);

		MailModel mail = new MailModel();
		mail.setSchlussel(schlussel);
		mail.setZweck(getEnum);
		mail.setLength(ctLength);
		mail.setPasswort(Base64.encodeBase64String(cipherText));

		super.setMail(mail);

		/*
		 * System.out.println("cipher text: " + new String(cipherText));
		 * System.out.println("cipher text2: " +
		 * Base64.encodeBase64(cipherText)); byte[] decoded =
		 * Base64.decodeBase64(Base64.encodeBase64(cipherText));
		 * System.out.println("cipher text3: " + new String(decoded));
		 */
	}

	/**
	 * 
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	private String Enschlusseln(MailModel mail) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		keyBytes = mail.getSchlussel().getBytes();
		SecretKeySpec key = new SecretKeySpec(keyBytes, "ARC4");
		Cipher cipher = Cipher.getInstance("ARC4", "BC");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = new byte[(int) mail.getLength()];
		int ptLength = cipher.update(Base64.decodeBase64(mail.getPasswort()),
				0, (int) mail.getLength(), plainText, 0);
		ptLength += cipher.doFinal(plainText, ptLength);
		return new String(plainText);
	}

	/*
	 * public void Test() throws Exception { Security.addProvider(new
	 * org.bouncycastle.jce.provider.BouncyCastleProvider());
	 * 
	 * byte[] input = "bestellung".getBytes(); byte[] keyBytes = "b".getBytes();
	 * SecretKeySpec key = new SecretKeySpec(keyBytes, "ARC4"); Cipher cipher =
	 * Cipher.getInstance("ARC4", "BC"); byte[] cipherText = new
	 * byte[input.length]; cipher.init(Cipher.ENCRYPT_MODE, key); int ctLength =
	 * cipher.update(input, 0, input.length, cipherText, 0); ctLength +=
	 * cipher.doFinal(cipherText, ctLength); System.out.println("cipher text: "
	 * + new String(cipherText)); System.out.println("cipher text2: " +
	 * Base64.encodeBase64(cipherText));
	 * 
	 * byte[] decoded = Base64.decodeBase64(Base64.encodeBase64(cipherText));
	 * 
	 * System.out.println("cipher text3: " + new String(decoded));
	 * 
	 * byte[] plainText = new byte[ctLength]; cipher.init(Cipher.DECRYPT_MODE,
	 * key); int ptLength = cipher.update(decoded, 0, ctLength, plainText, 0);
	 * ptLength += cipher.doFinal(plainText, ptLength);
	 * System.out.println("plain text : " + new String(plainText));
	 * 
	 * }
	 */
}