package com.rag.parser.config;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmbeddingModelSanityTest {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Test
    void shouldGenerateEmbeddingForSampleText() {
        Response<Embedding> response = embeddingModel.embed("Cancel my subscription");

        Embedding embedding = response.content();
        float[] vector = embedding.vector();

        System.out.println("Vector length: " + vector.length);
        System.out.println("First 5 values: ");
        for (int i = 0; i < 5; i++) {
            System.out.println(vector[i]);
        }

        assertThat(vector).isNotNull();
        assertThat(vector.length).isEqualTo(768);
    }
}
