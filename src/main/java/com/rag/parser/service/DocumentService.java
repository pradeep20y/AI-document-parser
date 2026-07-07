package com.rag.parser.service;

import com.rag.parser.entity.Document;
import com.rag.parser.exception.EmptyFileException;
import com.rag.parser.repository.DocumentRepository;
import com.rag.parser.storage.StorageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final StorageService storageService;
    private final PdfParserService pdfParserService;
    
    public Document save(String fileName) {
        Document document = new Document();

        document.setFileName(fileName);
        document.setUploadedAt(LocalDateTime.now());

        return documentRepository.save(document);
    }

    public void upload(MultipartFile file) throws IOException{
        if (file.isEmpty()){
            throw new EmptyFileException("Uploaded file is empty.");
        }
        String storedFileName = storageService.store(file);
        InputStream inputStream = file.getInputStream();

        System.out.println(pdfParserService.extractText(inputStream).get(35));
    }
}
