package com.bezkoder.springjwt.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.StandardCopyOption;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileSystemStorageService implements StorageService{
    //Lugar donde se almacenan los archivos
    @Value("${media.location}")
    private String mediaLocation;

    //Ruta de almacenamiento
    private Path rootLocation;

 @Override
 @PostConstruct
 public void init() throws IOException {
        //construimos las carpetas de almacenamiento
        rootLocation=Paths.get(mediaLocation);
        Files.createDirectories(rootLocation);

    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            String filename = file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println("destinationFile = " + destinationFile);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file. ", e);
        }
    }

    @Override
    public String storeAndResize(MultipartFile file, int maxWidth, int maxHeight) {
        try {
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }

            String filename = file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();

            // Redimensionar y comprimir la imagen directamente desde el archivo
            Thumbnails.of(file.getInputStream())
                    .size(maxWidth, maxHeight)
                    .outputQuality(0.8) // Ajusta la calidad de compresión según tus necesidades
                    .toFile(destinationFile.toFile());

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store or resize file. ", e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource((file.toUri()));
            if(resource.exists()|| resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("Could not read file: "+ filename);
            }
        }catch (MalformedURLException e){
            throw  new RuntimeException("Could not read file: "+ filename);

        }
    }
    @Override
    public File loadAsFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename).normalize().toAbsolutePath();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return file.toFile();
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

}
