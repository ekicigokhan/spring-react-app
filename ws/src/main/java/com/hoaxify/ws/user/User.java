package com.hoaxify.ws.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoaxify.ws.auth.token.Token;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean active = false;
    @JsonIgnore
    private String activationToken;
    @Lob
    private String image;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    //token'da hangi fild'a denk geliyor : Foreing key : user :)
    //Eğer USER remove edilirse ona bağlı olan TOKEN'larda REMOVE EDİLSİN.
    private List<Token> tokens;

    private String passwordResetToken;

}
