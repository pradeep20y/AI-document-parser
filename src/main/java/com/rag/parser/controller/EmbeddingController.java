package com.rag.parser.controller;

import com.rag.parser.service.EmbeddingService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/embeddings")
@RequiredArgsConstructor
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    @PostMapping("/process/{documentId}")
    public ResponseEntity<String> processEmbeddings(@PathVariable Long documentId) {
        int count = embeddingService.embedPendingChunks(documentId);
        return ResponseEntity.ok("Embedded " + count + " chunk(s).");
    }
}
