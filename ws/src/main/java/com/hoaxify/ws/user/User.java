package com.hoaxify.ws.user;

import com.hoaxify.ws.user.validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {

    @Id
    @GeneratedValue
    long id;

    @NotBlank(message = "{hoaxify.constrait.username.notblank}")
    @Size(min = 4, max = 25)
    private String username;

    @NotBlank()
    @Email()
    @UniqueEmail()
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoaxify.constrait.password.pattern}")
    private String password;

}
