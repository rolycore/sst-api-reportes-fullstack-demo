package com.bezkoder.springjwt.util;

import org.springframework.http.HttpHeaders;

import org.springframework.core.io.Resource;
import java.nio.file.Path;

public interface FileHandlerUtil {
    Path getByDirectoryAndFilename(final String DIRECTORY, final String filename);
    Resource getByPath(final Path filePath);
    HttpHeaders getByResource(final Resource resource);
}
