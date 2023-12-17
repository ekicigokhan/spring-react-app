package com.hoaxify.ws.user.dto;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.validation.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdate(
        @NotBlank(message = "{hoaxify.constrait.username.notblank}")
        @Size(min = 4, max = 25)
        String username,
        @FileType(types = {"jpeg", "png"}) // isteğe bağlı kontrollerimi değiştirebilirim.
        String image
) {

}



