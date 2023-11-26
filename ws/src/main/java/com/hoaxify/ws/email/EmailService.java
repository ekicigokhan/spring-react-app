package com.hoaxify.ws.email;

import com.hoaxify.ws.configuration.HoaxifyProperties;
import com.hoaxify.ws.shared.Messages;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    JavaMailSenderImpl mailSender;

    @Autowired
    HoaxifyProperties hoaxifyProperties;

    @PostConstruct
    public void initialize() {
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hoaxifyProperties.getEmail().host());
        mailSender.setPort(hoaxifyProperties.getEmail().port());
        mailSender.setUsername(hoaxifyProperties.getEmail().username());
        mailSender.setPassword(hoaxifyProperties.getEmail().password()); //Environment Variables'a ${HOAXIFY_EMAIL_PASSWORD} eklenecek.

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
    }

    String activationEmail = """
            <html>
                <body>
                    <h1>${title}</h1>
                    <h3><a href="${url}">${clickHere}</a></h3>
                </body>
            </html>
            """;

    public void sendActivationMail(String email, String activationToken) {
        var activationUrl = hoaxifyProperties.getClient().host() + "/activation/" + activationToken;

        var title = Messages.getMessageForLocale("hoaxify.mail.user.created.title", LocaleContextHolder.getLocale());
        var clickHere = Messages.getMessageForLocale("hoaxify.mail.click.here", LocaleContextHolder.getLocale());


        var mailBody = activationEmail
                .replace("${url}", activationUrl)
                .replace("${title}", title)
                .replace("${clickHere}", clickHere);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setFrom(hoaxifyProperties.getEmail().from());
            message.setTo(email);
            message.setSubject(title);
            message.setText(mailBody, true);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

        this.mailSender.send(mimeMessage);
    }
}
