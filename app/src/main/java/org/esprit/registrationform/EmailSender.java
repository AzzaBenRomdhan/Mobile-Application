package org.esprit.registrationform;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    // Mettez à jour ces informations avec les détails de votre compte de messagerie
    private static final String FROM_EMAIL = "salma.khemiri12345@gmail.com";
    private static final String FROM_PASSWORD = "Salma123@";

    public static boolean sendResetPasswordEmail(String userEmail) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("Password Reset");
            message.setText("Dear User,"
                    + "\n\nYour password has been reset successfully.");

            Transport.send(message);

            System.out.println("Password reset email sent successfully.");

            // Return true indicating successful email sending
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            // Return false if an exception occurs during email sending
            return false;
        }
    }
}
