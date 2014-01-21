package de.palaver.service.emailversand;

import java.io.File;
import java.net.ConnectException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


 /**
  * @author Mihail Boehm
  */
public class MailActions
{
	/**
	 * Properties
	 * @param acc
	 * @param recipient
	 * @param subject
	 * @param text
	 * @throws AddressException
	 * @throws MessagingException
	 */
    public static void sendOhneAnhang(MailAccounts acc, String recipient, String subject,
            String text) throws AddressException, MessagingException, ConnectException
    {
        // Properties über die Systemeigenschaften anlegen
        Properties properties = System.getProperties(); 
        // Server-Adresse hinzugefügen
        properties.setProperty("mail.smtp.host", acc.getSmtpHost());         
        // In diesem Fall nicht notwendig (da der Standardport 25 ist), aber
        // dennoch wissenswert ist das Setzen des Serverports
        // (für den Fall das beispielsweise die E-Mail verschlüsselt versendet werden soll)
        properties.setProperty("mail.smtp.port", String.valueOf(acc.getPort())); 
        // In der Regel wird nach Authentifizierungsdaten gefragt, weshalb
        // dies in den Properties expliziert angegeben werden muss
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");         
        // Eine (Standard)Session wird erstellt.
        // wird keine Authentifizierung benötigt, wird "null" als Attribut übertragen
        Session session = Session.getDefaultInstance(properties, acc.getPasswordAuthentication()); 
        // Eine neue Nachricht wird erzeugt
        MimeMessage msg = new MimeMessage(session); 
        // Von wem kommt die E-Mail?
        msg.setFrom(new InternetAddress(acc.getEmail())); 
        // Wohin soll die Reise gehen?
        // CC geht beispielsweise an Message.RecipientType.CC
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false)); 
        // Betreff
        msg.setSubject(subject);         
        // Nachricht
        msg.setText(text);         
        // E-Mail versenden
        Transport.send(msg);
    }
    
    public static void sendMitAnhang(MailAccounts acc, String recipient, String subject,
            String text, String filename) throws AddressException, MessagingException
    {
        // Properties über die Systemeigenschaften anlegen
        Properties properties = System.getProperties(); 
        // Server-Adresse hinzugefügen
        properties.setProperty("mail.smtp.host", acc.getSmtpHost());         
        // In diesem Fall nicht notwendig (da der Standardport 25 ist), aber
        // dennoch wissenswert ist das Setzen des Serverports
        // (für den Fall das beispielsweise die E-Mail verschlüsselt versendet werden soll)
        properties.setProperty("mail.smtp.port", String.valueOf(acc.getPort())); 
        // In der Regel wird nach Authentifizierungsdaten gefragt, weshalb
        // dies in den Properties expliziert angegeben werden muss
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");         
        // Eine (Standard)Session wird erstellt.
        // wird keine Authentifizierung benötigt, wird "null" als Attribut übertragen
        Session session = Session.getDefaultInstance(properties, acc.getPasswordAuthentication()); 
        // Eine neue Nachricht wird erzeugt
        MimeMessage msg = new MimeMessage(session);
        // Von wem kommt die E-Mail?
        msg.setFrom(new InternetAddress(acc.getEmail())); 
        // Wohin soll die Reise gehen?
        // CC geht beispielsweise an Message.RecipientType.CC
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false)); 
        // Betreff
        msg.setSubject(subject); 
     // Create the message part 
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(text);
     // Create a multipar message
        Multipart multipart = new MimeMultipart();
     // Set text message part
        multipart.addBodyPart(messageBodyPart);
     // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        //File file = new File("C:/WirelessDiagLog.csv");
        File file = new File(filename);
        messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
        messageBodyPart.setFileName(file.getName());
        messageBodyPart.setDescription(messageBodyPart.ATTACHMENT);
        multipart.addBodyPart(messageBodyPart);
     // Send the complete message parts
        msg.setContent(multipart );  
        msg.saveChanges();
        // E-Mail versenden
        Transport.send(msg);
    }
    
}
