import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;


public class Mailprotocol {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Laborator nr.4");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        JPanel panel = new JPanel();
        frame.add(panel);
        JButton button = new JButton("SMTP");
        JButton button1 = new JButton("POP3");
        panel.add(button);
        panel.add(button1);
        button.addActionListener(new Smtp());
        button1.addActionListener(new POP3());
    }

    static class Smtp implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            final String username = "kenny.mccormick443@gmail.com ";
            final String password = "kenny443";


            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "25");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("kenny.mccormick443@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("mihai.druta98@gmail.com"));
                message.setSubject("Subiectul mesajului ");
                message.setText("Mesaj SMTP trimis cu succes");
                Transport.send(message);
                System.out.println("Executat cu succes");
            } catch (MessagingException z) {
                throw new RuntimeException(z);
            }
        }
    }

    static class POP3 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String host = "pop.gmail.com";
            String mailStoreType = "pop3";

            String username = "kenny.mccormick443@gmail.com";
            String password = "kenny443";


            verid(host, mailStoreType, username, password);

        }

        private void verid(String host, String mailStoreType, String username, String password) {

            try {
                //create properties field
                Properties properties = new Properties();

                properties.put("mail.pop3.host", host);
                properties.put("mail.pop3.port", "110");
                properties.put("mail.pop3.starttls.enable", "true");
                Session emailSession = Session.getDefaultInstance(properties);

                //create the POP3 store object and connect with the pop server
                Store store = emailSession.getStore("pop3s");

                store.connect(host, username, password);

                //create the folder object and open it
                Folder emailFolder = store.getFolder("INBOX");
                emailFolder.open(Folder.READ_ONLY);

                // retrieve the messages from the folder in an array and print it
                Message[] messages = emailFolder.getMessages();
                System.out.println("Total messages is " + messages.length);
                for (int i = 0, n = messages.length; i < n; i++) {
                    if (i == 10) {break;}


                    Message message = messages[i];
                    System.out.println("\n");
                    System.out.println("Email Number " + (i + 1));
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    System.out.println("Text: " + message.getContent());

                }
                //close the store and folder objects
                emailFolder.close(false);
                store.close();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
