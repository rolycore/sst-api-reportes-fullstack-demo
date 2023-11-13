package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.DTO.FileHandlerDto;
import com.bezkoder.springjwt.impl.service.FileHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@CrossOrigin(origins = { "https://appicmlab.icmetrologia.com" })
@RestController
@RequestMapping("/api/v1/files")
@PreAuthorize("hasRole('ADMIN')")
public class FileHandlerResource {
    private final FileHandlerService fileHandlerService;

    @Autowired
    public FileHandlerResource(final FileHandlerService fileHandlerService) {
        this.fileHandlerService = fileHandlerService;
    }

    @PostMapping(value = { "/upload" })
    public ResponseEntity<List<String>> upload(@RequestParam("files") final List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok().body(this.fileHandlerService.upload(multipartFiles));
    }

    @GetMapping(value = {"/download/{filename}"})
    public ResponseEntity<Resource> download(@PathVariable("filename") final String filename) throws IOException {
        final FileHandlerDto fileHandlerDto = this.fileHandlerService.download(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(fileHandlerDto.getFilePath())))
                .headers(fileHandlerDto.getHeaders())
                .body(fileHandlerDto.getResource());
    }

}
