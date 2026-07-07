package com.rag.parser.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
 
@Service
public class PdfParserService {
    public List<String> extractText(InputStream inputStream) throws IOException {
        try (
            PDDocument document =
                Loader.loadPDF(new RandomAccessReadBuffer(inputStream))
                
        ) {
            System.out.println(document.getNumberOfPages());
            PDFTextStripper stripper = new PDFTextStripper();
            List<String> pages = new ArrayList<>();
            int totalPage = document.getNumberOfPages();

            for (int page = 1; page<=totalPage; page++) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);

                pages.add(stripper.getText(document));
            }
            
            return pages;
        }
    }
}
