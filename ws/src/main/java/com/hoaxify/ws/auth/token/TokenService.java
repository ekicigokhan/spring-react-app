package com.hoaxify.ws.auth.token;

import com.hoaxify.ws.auth.dto.Credentials;
import com.hoaxify.ws.user.User;

public interface TokenService {

    Token createToken(User user, Credentials creds);

    User verifyToken(String authorizationHeader);

    void logout(String authorizationHeader);

}
