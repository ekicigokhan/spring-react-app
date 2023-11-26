package com.hoaxify.ws.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoaxify.ws.user.User;
import lombok.Data;

@Data
public class UserDTO {

    private long id;

    private String username;

    private String email;

    private String image;

    public UserDTO(User user){
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setImage(user.getImage());
    }
}
