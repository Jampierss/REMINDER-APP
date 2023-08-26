package controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {

    final String fromEmail = System.getenv("EMAIL_USER");
    final String password = System.getenv("EMAIL_PASS");

    String toEmail;
    String subject;

    public SendEmail(String toEmail, String subject) {
        this.toEmail = toEmail;
        this.subject = subject;
    }

    public boolean Send() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.trust", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", fromEmail);
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message email = new MimeMessage(session);
            email.setFrom(new InternetAddress(fromEmail));
            email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.toEmail));
            email.setSubject(this.subject);
            email.setText(this.subject);

            Transport.send(email);
            System.out.println("Email was sent correctly!");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
            return false;
        }
    }
}
