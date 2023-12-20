package com.hoaxify.ws.auth.token;

import com.hoaxify.ws.auth.dto.Credentials;
import com.hoaxify.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "hoaxify.token-type", havingValue = "opaque")
public class OpaqueTokenService implements TokenService { // İçeriği anlaşılmayan, billgi barındırmayan token'a Opaque denir.

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public Token createToken(User user, Credentials creds) {
        String randomValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setToken(randomValue);
        token.setUser(user);
        return tokenRepository.save(token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        /*if (authorizationHeader == null) return null;
        var token = authorizationHeader.split(" ")[1];
        var tokenInDb = tokenRepository.findById(token);*/
        var tokenInDb = getToken(authorizationHeader);
        if (!tokenInDb.isPresent()) return null;
        return tokenInDb.get().getUser();
    }

    @Override
    public void logout(String authorizationHeader) {
        var tokenInDb = getToken(authorizationHeader);
        if (!tokenInDb.isPresent()) return;
        tokenRepository.delete(tokenInDb.get());
    }

    private Optional<Token> getToken(String authorizationHeader) {
        if (authorizationHeader == null) return Optional.empty();
        var token = authorizationHeader.split(" ")[1];
        return tokenRepository.findById(token);
    }
}
