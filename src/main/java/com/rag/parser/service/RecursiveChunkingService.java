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
    private static final int CHUNK_OVERLAP = 200;

    private final DocumentSplitter splitter = DocumentSplitters.recursive(CHUNK_SIZE,CHUNK_OVERLAP);
    
    private List<Chunk> chunk(String text, int pageNumber, int startingChunkIndex) {

        List<Chunk> chunks = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return chunks;
        }

        Document document = Document.from(text);

        List<TextSegment> segments = splitter.split(document);

        int chunkIndex = startingChunkIndex;

        for (TextSegment segment : segments) {

            String chunkText = segment.text();

            Chunk chunk = Chunk.builder()
                    .text(chunkText)
                    .pageNumber(pageNumber)
                    .chunkIndex(chunkIndex++)
                    .characterCount(chunkText.length())
                    .build();

            chunks.add(chunk);
        }

        return chunks;
    }

    @Override
    public List<Chunk> chunkAll(List<String> pageTexts) {

        List<Chunk> allChunks = new ArrayList<>();

        if (pageTexts == null || pageTexts.isEmpty()) {
            return allChunks;
        }

        int globalChunkIndex = 0;

        for (int pageIndex = 0; pageIndex < pageTexts.size(); pageIndex++) {

            int pageNumber = pageIndex + 1;

            List<Chunk> pageChunks = chunk(
                    pageTexts.get(pageIndex),
                    pageNumber,
                    globalChunkIndex
            );

            allChunks.addAll(pageChunks);

            globalChunkIndex += pageChunks.size();
        }

        return allChunks;
    }
} 
