package com.hoaxify.ws.auth.dto;

import com.hoaxify.ws.auth.token.Token;
import com.hoaxify.ws.user.dto.UserDTO;
import lombok.Data;

@Data
public class AuthResponse {

    UserDTO user;

    Token token;

}
