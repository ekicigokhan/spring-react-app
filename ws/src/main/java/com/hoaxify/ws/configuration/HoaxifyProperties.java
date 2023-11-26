package com.hoaxify.ws.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "hoaxify") // Hoaxify ile başlayan config'ler bu class'da tutulacak.
@Configuration //prefix'teki propertyleri bu classdaki değerlere assing etmiş olacak.
public class HoaxifyProperties {

    private Email email;

    private Client client;

    public static record Email(
            String username,
            String password,
            String host,
            int port,
            String from
    ) {
    }

    public static record Client(
            String host
    ) {
    }
}
