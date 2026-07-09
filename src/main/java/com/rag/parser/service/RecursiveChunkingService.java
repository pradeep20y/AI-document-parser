package com.rag.parser.service;

 
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.rag.parser.entity.Chunk;
import com.rag.parser.service.interfaces.ChunkingService;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;

@Service
public class RecursiveChunkingService implements ChunkingService {

    private static final int CHUNK_SIZE = 1000;
    private static final int CHUNK_OVERLAP = 150;

    private final DocumentSplitter splitter = DocumentSplitters.recursive(CHUNK_SIZE,CHUNK_OVERLAP);
    
    @Override
    public List<Chunk> chunk (String text) {
        List<Chunk> chunks = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return chunks;
        }

        Document document = Document.from(text.trim());
        List<TextSegment> segments = splitter.split(document);

        for (TextSegment segment : segments) {
            chunks.add(new Chunk(segment.text()));
        }

        return chunks;
    }

    @Override
    public List<Chunk> chunkAll(List<String> texts) {
        List<Chunk> allChunks = new ArrayList<>();
        for (String text : texts) {
            allChunks.addAll(chunk(text));
        }
        return allChunks;
    }
} 
