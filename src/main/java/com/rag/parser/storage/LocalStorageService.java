package com.rag.parser.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rag.parser.exception.FileTooLargeException;
import com.rag.parser.exception.InvalidFileNameException;
import com.rag.parser.exception.InvalidFileTypeException;

@Service
public class LocalStorageService implements StorageService {

    private final Path uploadPath;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    public LocalStorageService( @Value("${app.storage.upload-dir}") String uploadDir) {
        this.uploadPath = Paths.get(uploadDir);
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize upload directory", e);
        }
    }
        
    @Override
    public String store(MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new InvalidFileNameException("Filename is missing.");
        }

        String extension = getExtension(originalFileName);
        if (!extension.equalsIgnoreCase("pdf")) {
            throw new InvalidFileTypeException("Only PDF files are allowed.");
        }

        String contentType = file.getContentType();
        if (!"application/pdf".equals(contentType)) {
            throw new InvalidFileTypeException("Only PDF files are allowed.");
        }
        
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileTooLargeException("Maximum file size is 10 MB.");
        }

        String storedFileName = generateStoredFileName(originalFileName);

        Path destination = uploadPath.resolve(storedFileName);

        try {
            file.transferTo(destination);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        return storedFileName;
    }

    private String generateStoredFileName(String originalFileName) {
        String extension = "";

        int lastDotIndex = originalFileName.lastIndexOf('.');

        if (lastDotIndex != -1 && lastDotIndex != originalFileName.length() - 1) {
            extension = originalFileName.substring(lastDotIndex);
        }

        return UUID.randomUUID() + extension;
    }

    private String getExtension(String fileName) {

        int lastDot = fileName.lastIndexOf('.');

        if (lastDot == -1 || lastDot == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(lastDot + 1);
    }
}