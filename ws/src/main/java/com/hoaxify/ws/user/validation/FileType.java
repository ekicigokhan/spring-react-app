package com.hoaxify.ws.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(
        validatedBy = {FileTypeValidator.class} //Validaton logic'imizin nerede oldugunu yazacağız.
)
@Target({ElementType.FIELD}) //Bu anotasyon nerede kullanılabilir ?
@Retention(RetentionPolicy.RUNTIME)//Ugulamamız çalıştığı süre boyunca bu anotasyonun process edilebilmesini istiyoruz.
public @interface FileType {

    String message() default "Only {types} are allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] types();
}
