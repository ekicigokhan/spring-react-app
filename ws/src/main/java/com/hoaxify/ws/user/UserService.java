package com.hoaxify.ws.user;

import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.save(user);
            sendActivationMail(user);
        } catch (DataIntegrityViolationException ex){
            throw new NotUniqueEmailException();
        }
    }

    private void sendActivationMail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@my-app.com");
        message.setTo(user.getEmail());
        message.setSubject("Account Activation !");
        message.setText("http://localhost:5173/activation/" + user.getActivationToken());
        getJavaMailSender().send(message);
    }

    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.ethereal.email");
        mailSender.setPort(587);
        mailSender.setUsername("jeremie.eichmann60@ethereal.email");
        mailSender.setPassword("Vy3hz85z9zkcG3ePfR");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable","true");
        return mailSender;
    }
}
