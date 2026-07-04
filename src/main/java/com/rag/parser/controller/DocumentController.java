package com.rag.parser.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rag.parser.entity.Document;
import com.rag.parser.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public Document createDocument(@RequestParam String fileName) {
        return documentService.save(fileName);
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) {

        System.out.println("Filename : " + file.getOriginalFilename());
        System.out.println("Size     : " + file.getSize());
        System.out.println("Type     : " + file.getContentType());
    try {
        Thread.sleep(60_000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
        return "Uploaded";
    }

}
