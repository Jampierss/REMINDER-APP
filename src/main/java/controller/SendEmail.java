package controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Scanner;

public class SendEmail {
    public static void main(String[] args) {

        final String fromEmail = "jeampiers20052005@gmail.com";
        final String password = "bmsygjpzkxyefpca";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the destination email: ");
        String destination = scanner.nextLine();

        System.out.println("Enter the reminder: ");
        String subject = scanner.nextLine();

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
            email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
            email.setSubject(subject);
            email.setText(subject);

            Transport.send(email);
            System.out.println("Email was sent correctly!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
        }


    }
}
