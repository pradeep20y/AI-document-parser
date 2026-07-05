package com.rag.parser.service;

import com.rag.parser.entity.Document;
import com.rag.parser.exception.EmptyFileException;
import com.rag.parser.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public Document save(String fileName) {
        Document document = new Document();

        document.setFileName(fileName);
        document.setUploadedAt(LocalDateTime.now());

        return documentRepository.save(document);
    }

    public void upload(MultipartFile file) {
        if (file.isEmpty()){
            throw new EmptyFileException("Uploaded file is empty.");
        }



    }
}
