package com.rag.parser.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rag.parser.entity.Chunk;
import com.rag.parser.exception.DocumentNotFoundException;
import com.rag.parser.repository.ChunkRepository;
import com.rag.parser.repository.DocumentRepository;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
 
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmbeddingService {
    private final ChunkRepository chunkRepository;
    private final EmbeddingModel embeddingModel;
    private final DocumentRepository documentRepository;

    public int embedPendingChunks(Long documentId) {

        if (!documentRepository.existsById(documentId)) {
            throw new DocumentNotFoundException(documentId + "Not found");
        }
        List<Chunk> pendingChunks = chunkRepository.findByDocument_IdAndEmbeddingIsNull(documentId);

        if (pendingChunks.isEmpty()) {
            return 0;
        }

        List<TextSegment> segments = pendingChunks.stream()
                .map(chunk -> TextSegment.from(chunk.getText()))
                .toList();

        Response<List<Embedding>> response = embeddingModel.embedAll(segments);
        List<Embedding> embeddings = response.content();

        if (embeddings.size() != pendingChunks.size()) {
            throw new IllegalStateException(
                "Embedding count mismatch: expected " + pendingChunks.size()
                    + " but got " + embeddings.size()
            );
        }

        for (int i = 0; i < pendingChunks.size(); i++) {
            Chunk chunk = pendingChunks.get(i);
            float[] vector = embeddings.get(i).vector();
            chunk.setEmbedding(vector);
        }

        chunkRepository.saveAll(pendingChunks);

        return pendingChunks.size();
    }
}
