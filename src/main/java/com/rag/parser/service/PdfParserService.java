package com.rag.parser.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
 
@Service
public class PdfParserService {
    public String extractText(InputStream inputStream) throws IOException {
        try (
            PDDocument document =
                Loader.loadPDF(new RandomAccessReadBuffer(inputStream))
                
        ) {
            System.out.println(document.getNumberOfPages());
            return "";
        }
    }
}
