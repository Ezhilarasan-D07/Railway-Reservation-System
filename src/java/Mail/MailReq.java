package Mail;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.*;

public class MailReq {

    static final String senderEmail = "railwayreservation95@gmail.com";
    static final String appPassword = "lbqu yhaa rbyg jdtz";

    public static boolean sendEmail(String email, String otp) {

        Properties props = new Properties();
        
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(email)
            );
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);

            Transport.send(message);
            System.out.println("📧 OTP sent successfully to " + email);
            System.out.println("existed");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }    
}
