package com.hoaxify.ws.auth.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoaxify.ws.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class Token {

    @Id
    String token;
    @Transient //DB'ye yazılmasını istemiyoruz.
    String prefix = "Bearer";
    @JsonIgnore
    @ManyToOne
    User user;

    public Token(String prefix, String token) {
        this.prefix = prefix;
        this.token = token;
    }

    public Token() {
    }
}
