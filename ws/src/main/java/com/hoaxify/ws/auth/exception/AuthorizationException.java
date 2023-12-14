package com.hoaxify.ws.auth.exception;

import com.hoaxify.ws.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException(long id){
        super(Messages.getMessageForLocale("hoaxify.auth.forbidden.request", LocaleContextHolder.getLocale(),id));
    }
}
