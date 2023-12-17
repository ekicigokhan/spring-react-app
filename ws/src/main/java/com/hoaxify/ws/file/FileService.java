package com.hoaxify.ws.file;

import com.hoaxify.ws.configuration.HoaxifyProperties;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService { // target'a yani root directory'e save edeceğiz.

    @Autowired
    HoaxifyProperties hoaxifyProperties;

    Tika tika = new Tika();

    public String saveBase64StringAsFile(String image) { //image'ı byte array'e çevirmemiz gerekiyor. çünkü onu output stream'ın write metoduna vereceğiz oda dosya olarak kaydetmiş olacak.
        String fileName = UUID.randomUUID().toString();
        Path path = getProfileImagePath(fileName);
        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());

            outputStream.write(decodedImage(image));
            outputStream.close(); // Yazım işlemi bitince close ediyoruz.
            return fileName;

        } catch (IOException e) { // IO Exception FileNotFoundExp ide kapsıyor.
            e.printStackTrace();
        }
        return null;
    }

    public String detectType(String value) {
        return tika.detect(decodedImage(value));
    }

    private byte[] decodedImage(String encodedImage){
        return Base64.getDecoder().decode(encodedImage.split(",")[1]);
    }

    public void deleteprofileImage(String image) {
        if (image == null) return;
        Path path = getProfileImagePath(image);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getProfileImagePath(String fileName){
        return Paths.get(hoaxifyProperties.getStorage().getRoot(),hoaxifyProperties.getStorage().getProfile(),fileName);
    }
}
