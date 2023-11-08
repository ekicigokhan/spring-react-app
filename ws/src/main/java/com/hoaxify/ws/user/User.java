package com.hoaxify.ws.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    long id;

    @NotBlank(message = "Username cannot be blank !")
    @Size(min = 4, max = 25, message = "Size must be between 4 and 25")
    private String username;

    @NotBlank(message = "E-mail cannot be blank !")
    @Email()
    private String email;


/*
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,15}$", message = "Şifre gereksinimlerini karşılamıyor")
*/
    private String password;

}
