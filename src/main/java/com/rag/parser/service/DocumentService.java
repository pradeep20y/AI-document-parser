package com.rag.parser.service;

import com.rag.parser.entity.Chunk;
import com.rag.parser.entity.Document;
import com.rag.parser.exception.EmptyFileException;
import com.rag.parser.repository.ChunkRepository;
import com.rag.parser.repository.DocumentRepository;
import com.rag.parser.service.interfaces.ChunkingService;
import com.rag.parser.storage.StorageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ChunkRepository chunkRepository;
    private final StorageService storageService;
    private final PdfParserService pdfParserService;
    private final ChunkingService chunkingService;
    
    public Document save(String fileName) {
        Document document = new Document();

        document.setFileName(fileName);
        document.setUploadedAt(LocalDateTime.now());

        return documentRepository.save(document);
    }

    @Transactional
    public void upload(MultipartFile file) throws IOException{

        if (file.isEmpty()){
            throw new EmptyFileException("Uploaded file is empty.");
        }
        String storedFileName = storageService.store(file);

        Document document = new Document();
        document.setFileName(storedFileName);
        document.setUploadedAt(LocalDateTime.now());
        document = documentRepository.save(document);

        InputStream inputStream = file.getInputStream();

        List<String> extractedPage = pdfParserService.extractText(inputStream);
        List<Chunk> chunks = chunkingService.chunkAll(extractedPage);

        for (Chunk chunk : chunks) {
            System.err.println(chunk);
            chunk.setDocumentId(document);
        }
        
        chunkRepository.saveAll(chunks);
    } 

}
