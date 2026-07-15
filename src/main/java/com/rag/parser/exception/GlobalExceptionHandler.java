package com.rag.parser.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<String> handleEmptyFileException(EmptyFileException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(InvalidFileNameException.class)
    public ResponseEntity<String> handleEmptyFileName(InvalidFileNameException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<String> handleEmptyFileName(InvalidFileTypeException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatusCode.valueOf(400));
    }
    
    @ExceptionHandler(FileTooLargeException.class)
    public ResponseEntity<String> handleEmptyFileName(FileTooLargeException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleUploadLimit(MaxUploadSizeExceededException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatusCode.valueOf(413));
    }
    
    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<String> handleFileNotFound(DocumentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatusCode.valueOf(400));
    }    
}