package com.hoaxify.ws.auth;

import com.hoaxify.ws.auth.dto.AuthResponse;
import com.hoaxify.ws.auth.dto.Credentials;
import com.hoaxify.ws.shared.GenericMessage;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/api/v1/auth")
    ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody Credentials creds) {
        var authResponse = authService.authenticate(creds);
        var cookie = ResponseCookie.from("hoax-token", authResponse.getToken().getToken())
                .path("/")
                .httpOnly(true)
                .build(); // Tüm pathlerde geçerli.

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
    }

    @PostMapping("/api/v1/logout")
    ResponseEntity<GenericMessage> handleLogout(@RequestHeader(name = "Authorization", required = false)
                                                String authorizationHeader,
                                                @CookieValue(name = "hoax-token", required = false)
                                                String cookieValue) {

        var tokenWithPrefix = authorizationHeader;
        if (cookieValue != null){
            tokenWithPrefix = "AnyPrefix " + cookieValue;
        }
        authService.logout(tokenWithPrefix);
        var cookie = ResponseCookie.from("hoax-token", "")
                .path("/")
                .maxAge(0) // expire olmuş olacak.
                .httpOnly(true)
                .build(); // Tüm pathlerde geçerli.
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new GenericMessage("Logout success."));
    }
}
