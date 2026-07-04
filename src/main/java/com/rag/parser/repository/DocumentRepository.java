package com.rag.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rag.parser.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}