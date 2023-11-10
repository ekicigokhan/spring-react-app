package com.hoaxify.ws.user.validation;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> { //1.anotasyonun kendisi, kullanıldığı field'ın obje tipi : email
    // spring.data.jpa validationu akışı içerisinde bu isValid metodu çağrlacak.

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) { // s : email'in değeri

        User inDB = userRepository.findByEmail(s);
        if (inDB != null) {
            return false;
        }
        return true;
    }

}