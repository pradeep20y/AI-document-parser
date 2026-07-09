package com.rag.parser.service.interfaces;

import java.util.List;

import com.rag.parser.entity.Chunk;


public interface ChunkingService {
    List<Chunk> chunk(String text);

    List<Chunk> chunkAll(List<String> extractedText);
}
