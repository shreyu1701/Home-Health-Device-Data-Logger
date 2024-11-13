package com.zodiac.homehealthdevicedatalogger.Util;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class EmailService {



    public void sendPasswordResetLink(String email) throws MessagingException {
        final String senderEmail = "healthdatatracker5@gmail.com";
        final String senderPassword = "lypdglpatncnrkpl";
        final String subject = "Password Reset Request";

        String realPassword = PasswordRetrieverJson.getPasswordForEmail(email);
//        final String resetLink = "https://yourapp.com/reset-password?token=someUniqueToken";

        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Get session
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create email message
             Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText("Password for your Account is :\n\n" + realPassword);
//
            // Send email
            Transport.send(message);
            System.out.println("Password reset link sent to: " + email);

        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error in sending email: " + e.getMessage());
        }
    }

}
