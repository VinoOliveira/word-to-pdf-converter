package com.vino.convert_pdf;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WordToPDFConverter {
    public static void convert(String inputPath, String outputPath) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputPath);
             XWPFDocument wordDoc = new XWPFDocument(fis);
             PdfWriter writer = new PdfWriter(new FileOutputStream(outputPath));
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document pdfDocument = new Document(pdfDoc)) {

            for (XWPFParagraph para : wordDoc.getParagraphs()) {
                pdfDocument.add(new Paragraph(para.getText()));
            }
        }
    }
}
