package com.rag.parser.exception;

public class EmptyFileException extends RuntimeException {
    public EmptyFileException(String message){
        super(message);
    }
}
