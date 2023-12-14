package com.hoaxify.ws.user;

import com.hoaxify.ws.auth.exception.AuthorizationException;
import com.hoaxify.ws.auth.token.TokenService;
import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.dto.UserCreate;
import com.hoaxify.ws.user.dto.UserDTO;
import com.hoaxify.ws.user.dto.UserUpdate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @PostMapping("/api/v1/users")
    GenericMessage createUser(@Valid @RequestBody UserCreate user) {
        userService.save(user.toUser());
        String message = Messages.getMessageForLocale("hoaxify.create.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @PatchMapping("/api/v1/users/{token}/active")
    GenericMessage activateUser(@PathVariable String token) {
        userService.activateUser(token);
        String message = Messages.getMessageForLocale("hoaxify.activate.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @GetMapping("/api/v1/users")
    Page<UserDTO> getUsers(Pageable page, @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
        var loggedInUser = tokenService.verifyToken(authorizationHeader);
        return userService.getUsers(page, loggedInUser).map(UserDTO::new); //Constructor referans
    }

    @GetMapping("/api/v1/users/{id}")
    UserDTO getUserById(@PathVariable long id) {
        return new UserDTO(userService.getUser(id));
    }

    @PutMapping("/api/v1/users/{id}")
    UserDTO updateUser(@PathVariable long id, @Valid @RequestBody UserUpdate userUpdate, @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
        var loggedInUser = tokenService.verifyToken(authorizationHeader);
        if (loggedInUser == null || loggedInUser.getId() != id){
            throw new AuthorizationException(id);
        }
        return new UserDTO(userService.updateUser(id, userUpdate));
    }


}
