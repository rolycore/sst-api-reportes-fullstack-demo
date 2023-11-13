package com.bezkoder.springjwt.impl.service;

import com.bezkoder.springjwt.DTO.FileHandlerDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileHandlerService {
    List<String> upload(final List<MultipartFile> multipartFiles);
    FileHandlerDto download(final String filename);
}
