package com.hoaxify.ws.user;

import com.hoaxify.ws.configuration.CurrentUser;
import com.hoaxify.ws.email.EmailService;
import com.hoaxify.ws.file.FileService;
import com.hoaxify.ws.user.dto.PasswordResetRequest;
import com.hoaxify.ws.user.dto.PasswordUpdate;
import com.hoaxify.ws.user.dto.UserUpdate;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.InvalidTokenException;
import com.hoaxify.ws.user.exception.NotFoundException;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    FileService fileService;

    @Transactional(rollbackOn = MailException.class) // Belli şartlarda db ye kaydet. İşlem yapılmayacak ise geri al.
    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user);
            emailService.sendActivationEmail(user.getEmail(), user.getActivationToken());
        } catch (DataIntegrityViolationException ex) {
            throw new NotUniqueEmailException();
        } catch (MailException ex) {
            throw new ActivationNotificationException();
        }
    }

    public void activateUser(String token) {
        User inDb = userRepository.findByActivationToken(token);
        if (inDb == null) {
            throw new InvalidTokenException();
        }
        inDb.setActive(true);
        inDb.setActivationToken(null);
        userRepository.save(inDb);
    }


    Page<User> getUsers(Pageable page, CurrentUser currentUser) {
        if (currentUser == null) {
            return userRepository.findAll(page);
        }
        return userRepository.findByIdNot(currentUser.getId(), page);
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User updateUser(long id, UserUpdate userUpdate) {
        User inDb = getUser(id);
        inDb.setUsername(userUpdate.username());
        if (userUpdate.image() != null) {
            String fileName = fileService.saveBase64StringAsFile(userUpdate.image());
            fileService.deleteprofileImage(inDb.getImage());
            inDb.setImage(fileName);
        }
        return userRepository.save(inDb);
    }

    public void deleteUser(long id) {
        User userInDb = getUser(id);
        if (userInDb.getImage() != null) {
            fileService.deleteprofileImage(userInDb.getImage());
        }
        userRepository.deleteById(id);
    }

    public void handleResetRequest(PasswordResetRequest passwordResetRequest) {
        try {
            User userInDb = userRepository.findByEmail(passwordResetRequest.email());
            if (userInDb == null) throw new NotFoundException(0);
            userInDb.setPasswordResetToken(UUID.randomUUID().toString());
            userRepository.save(userInDb);
            emailService.sendPasswordResetEmail(userInDb.getEmail(), userInDb.getPasswordResetToken());
        } catch (DataIntegrityViolationException ex) {
            throw new NotUniqueEmailException();
        } catch (MailException ex) {
            throw new ActivationNotificationException();
        }
    }

    public void updatePassword(String token, PasswordUpdate passwordResetRequest) {
        User userInDb = userRepository.findByPasswordResetToken(token);
        if (userInDb == null){
            throw new InvalidTokenException();
        }
        userInDb.setPasswordResetToken(null);
        userInDb.setPassword(passwordEncoder.encode(passwordResetRequest.password()));
        userInDb.setActive(true);
        
        userRepository.save(userInDb);
    }
}
