package com.hoaxify.ws.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String email;

    private String password;

    private boolean active = false;

    private String activationToken;

}
