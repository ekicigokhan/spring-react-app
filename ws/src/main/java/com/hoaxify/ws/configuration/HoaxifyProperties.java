package com.hoaxify.ws.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Data
@ConfigurationProperties(prefix = "hoaxify") // Hoaxify ile başlayan config'ler bu class'da tutulacak.
@Configuration //prefix'teki propertyleri bu classdaki değerlere assing etmiş olacak.
public class HoaxifyProperties {

    private Email email;

    private Client client;

    private Storage storage = new Storage(); // default değerlerle initialize olabilmesi için instance oluştu.

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

    @Data
    public static class Storage{
        String root = "uploads";
        String profile = "profile";
    }
}
