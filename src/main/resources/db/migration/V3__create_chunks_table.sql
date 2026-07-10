CREATE TABLE chunks (
    id BIGSERIAL PRIMARY KEY,

    document_id BIGINT NOT NULL,

    text TEXT NOT NULL,

    page_number INTEGER NOT NULL,

    chunk_index INTEGER NOT NULL,

    character_count INTEGER NOT NULL,

    CONSTRAINT fk_chunk_document
        FOREIGN KEY (document_id)
        REFERENCES documents(id)
);