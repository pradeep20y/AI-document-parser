package com.rag.parser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rag.parser.entity.Chunk;

@Repository
public interface ChunkRepository extends JpaRepository<Chunk, Long> {
 }
