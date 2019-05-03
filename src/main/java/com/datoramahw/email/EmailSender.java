package com.datoramahw.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailSender {
    private final String userName = "polina.dulman.testing.email@gmail.com";
    private final String password = "verystrongtestpassword1!";

    private final String fromAddress = userName;
    private final String subject = "Waze Direction Calculations";

    public void send (String toAddress, String message) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setTLS(true);
        email.setAuthenticator(new DefaultAuthenticator(userName, password));
        email.setFrom(fromAddress);
        email.setSubject(subject);
        email.setMsg(message);
        email.addTo(toAddress);
        email.send();
    }
}
