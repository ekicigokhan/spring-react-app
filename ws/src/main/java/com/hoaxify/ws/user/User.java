package com.hoaxify.ws.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    long id;
    @NotBlank(message = "Username cannot be blank !")
    String username;
    @NotBlank(message = "E-mail cannot be blank !")
    String email;
    @Pattern(regexp = "'^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])$'")
    String password;
}
