package com.bezkoder.springjwt.util.controller;

import com.bezkoder.springjwt.util.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource; // Asegúrate de importar la clase Resource desde org.springframework.core.io
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = { "https://appicmlab.icmetrologia.com" })//https://appicmlab.icmetrologia.com
@RestController
@RequestMapping("media")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class MediaController {
    private final StorageService storageService;
    private final HttpServletRequest request;

    //metodo para almacenar y consultar los archivos
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>>uploadFile(@RequestParam("file")MultipartFile multipartFile){
        String path = storageService.store(multipartFile);
        String scheme = request.getScheme(); // Obtener el esquema (http o https)
        String serverName = request.getServerName(); // Obtener el nombre del servidor (host)
        int serverPort = request.getServerPort(); // Obtener el puerto del servidor

        // Construir la URL base con el esquema, host y puerto
        String host = scheme + "://" + serverName + ":" + serverPort;

        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/media/")
                .path(path)
                .toUriString();

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("uri", url);

        return  ResponseEntity.ok(resultMap);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/subir")
    public ResponseEntity<Map<String, String>> uploadAndResizeFile(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam(name = "maxWidth", defaultValue = "0") int maxWidth,
            @RequestParam(name = "maxHeight", defaultValue = "0") int maxHeight) {
        String path = storageService.storeAndResize(multipartFile, maxWidth, maxHeight);

        String scheme = request.getScheme(); // Obtener el esquema (http o https)
        String serverName = request.getServerName(); // Obtener el nombre del servidor (host)
        int serverPort = request.getServerPort(); // Obtener el puerto del servidor

        // Construir la URL base con el esquema, host y puerto
        String host = scheme + "://" + serverName + ":" + serverPort;

        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/media/")
                .path(path)
                .toUriString();

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("uri", url);
        System.out.println("resultMap = " + resultMap);
        return ResponseEntity.ok(resultMap);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR') or hasRole('ANONYMOUSUSER')")
    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        File file = storageService.loadAsFile(filename);
        String contentType = Files.probeContentType(file.toPath());

        // Crear un UrlResource para el archivo
        Resource resource = new UrlResource(file.toURI());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource); // Devolver el recurso (Resource) en lugar de intentar castear el archivo (File)
    }

}
