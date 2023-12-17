package com.hoaxify.ws.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration implements WebMvcConfigurer { //uploads folder'a erişmek lazım.

    @Autowired
    HoaxifyProperties hoaxifyProperties;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = Paths.get(hoaxifyProperties.getStorage().getRoot()).toAbsolutePath().toString() + "/"; //olmazsa hata verir /
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("file:" + path)
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }
    // URL'de assetsle başlayan şeyler için resource olarak "file:" 'a bakabilirsin.

    @Bean
    CommandLineRunner createStorageDirectories(){
        return args -> {
            createFolder(Paths.get(hoaxifyProperties.getStorage().getRoot())); //storage root oluşuyor...
            createFolder(Paths.get(hoaxifyProperties.getStorage().getRoot(), hoaxifyProperties.getStorage().getProfile()));
        };
    }
    private void createFolder(Path path){
        File file = path.toFile();
        boolean isFolderExist = file.exists() && file.isDirectory(); // folder bakıyoruz aslında
        if (!isFolderExist){
            file.mkdir();
        }
    }
}
