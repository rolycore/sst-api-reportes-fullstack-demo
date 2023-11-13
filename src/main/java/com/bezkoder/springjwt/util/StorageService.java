package com.bezkoder.springjwt.util;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface StorageService {
    void init() throws IOException;
    String store(MultipartFile file);

    String storeAndResize(MultipartFile file, int maxWidth, int maxHeight);

    Resource loadAsResource(String filename);
    File loadAsFile(String filename);
}
