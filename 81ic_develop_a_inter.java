Java
import java.util.Scanner;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class DevOpsPipelineNotifier {

    private static final String USERNAME = "your_email_username";
    private static final String PASSWORD = "your_email_password";
    private static final String RECEIVER_EMAIL = "receiver_email_address";
    private static final String SENDER_EMAIL = "sender_email_address";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter pipeline status (success/failure): ");
        String pipelineStatus = scanner.nextLine();

        if (pipelineStatus.equalsIgnoreCase("success")) {
            sendNotification("Pipeline execution successful!");
        } else if (pipelineStatus.equalsIgnoreCase("failure")) {
            sendNotification("Pipeline execution failed!");
        } else {
            System.out.println("Invalid pipeline status. Please enter 'success' or 'failure'.");
        }
    }

    private static void sendNotification(String message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "your_smtp_host");
        props.put("mail.smtp.port", "your_smtp_port");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            MimeMessage messageObj = new MimeMessage(session);
            messageObj.setFrom(new InternetAddress(SENDER_EMAIL));
            messageObj.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(RECEIVER_EMAIL));
            messageObj.setSubject("DevOps Pipeline Notification");
            messageObj.setText(message);

            Transport.send(messageObj);
            System.out.println("Notification sent successfully!");
        } catch (Exception e) {
            System.out.println("Error sending notification: " + e.getMessage());
        }
    }
}