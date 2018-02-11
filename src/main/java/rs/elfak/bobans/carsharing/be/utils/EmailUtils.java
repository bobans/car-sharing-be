package rs.elfak.bobans.carsharing.be.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Properties;

public class EmailUtils {

    public static void resetPasswordEmail(String email, String username, String password) {
        try {
            // Step1
            Properties mailServerProperties = createProps();

            // Step2
            System.out.println("===> get Mail Session..");
            Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            MimeMessage generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            generateMailMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress("drive.together.elfak@gmail.com"));
            generateMailMessage.setSubject("[Drive Together] Password reset");
            String emailBody;
            if (username != null && password != null) {
                emailBody = createResetPasswordEmailBody(username, password);
            } else {
                emailBody = createResetPasswordEmptyEmailBody();
            }
            generateMailMessage.setContent(emailBody, "text/html");
            System.out.println("Mail Session has been created successfully..");

            // Step3
            sendEmail(getMailSession, generateMailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String createResetPasswordEmailBody(String username, String password) {
        return "Dear customer,<br><br>" +
                "Your account details:<br>" +
                "username: <b>" + username + "</b><br>" +
                "password: <b>" + password + "</b><br>" +
                "If you did not request a password reset from DriveTogether Account, please ignore this message.<br><br>" +
                "Yours truly,<br>" +
                "DriveTogether Team";
    }

    private static String createResetPasswordEmptyEmailBody() {
        return "Dear customer,<br><br>" +
                "We haven't found account associated with your email address.<br><br>" +
                "If you did not request a password reset from DriveTogether Account, please ignore this message.<br><br>" +
                "Yours truly,<br>" +
                "DriveTogether Team";
    }

    private static void sendEmail(Session session, MimeMessage message) throws MessagingException {
        System.out.println("===> Get Session and Send mail");
        Transport transport = session.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "drive.together.elfak@gmail.com", "elfak2018");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

        System.out.println("Email sent to: " + Arrays.toString(message.getAllRecipients()));
    }

    private static Properties createProps() {
        System.out.println("===> setup Mail Server Properties..");
        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        System.out.println("Mail Server Properties have been setup successfully..");
        return mailServerProperties;
    }

}
