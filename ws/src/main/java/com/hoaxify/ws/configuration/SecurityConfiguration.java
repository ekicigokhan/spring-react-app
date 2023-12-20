package com.hoaxify.ws.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // Spring buradan bir Bean initialize edecek.
@EnableWebSecurity // Hangi end-point lerin secure olması gerektiğine karar vereceğiz.
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    TokenFilter tokenFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authentication) ->
                authentication
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/v1/users/{id}"))
                        .authenticated()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/v1/users/{id}"))
                        .authenticated()
                        .anyRequest()
                        .permitAll()
        );
        /*http.httpBasic(Customizer.withDefaults());*/ // basic ile ilgili default ayarlar sağlanıyor.
        http.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new AuthEntryPoint()));
        http.csrf(csrf -> csrf.disable()); // csrf veren bir lambda func ve onun disable() metodunu çağırabiliriz.
        http.headers(headers -> headers.disable());

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class); //bundan önce çalış.

        return http.build();
    }


}
